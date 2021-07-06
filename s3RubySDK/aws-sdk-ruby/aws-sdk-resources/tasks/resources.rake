namespace :resources do
  desc "Validates resource definitions"
  task :validate do
  end

  desc "Formats resource definitions"
  task :format do
    require 'json'
    Dir.glob("aws-sdk-core/apis/*.resources.json").each do |path|
      puts path
      data = File.open(path, 'r', encoding:'UTF-8') { |f| f.read }
      data = ResourceDefinitionFormatter.new.format_json(JSON.load(data))
      File.open(path, 'w', encoding:'UTF-8') { |f| f.write(data + "\n") }
    end
  end

end

# @api private
class ResourceDefinitionFormatter

  def format_json(data)
    json = JSON.pretty_generate(sort(data), indent: '  ', space: ' ')
    stack = [[]]
    json.lines.each do |line|
      if line.match(/({|\[)$/)
        stack.push([])
      end
      stack.last.push(line)
      if line.match(/(}|\]),?$/)
        frame = stack.pop
        reduce_whitespace(frame) if should_reduce?(frame)
        stack.last.push(frame.join)
      end
    end
    stack.last.join
  end

  private

  def sort(definition)
    ResourceDefinitionSorter.new.sort(definition)
  end

  def single_element?(frame)
    frame.size == 3 && !frame[1].match(/[{}]/)
  end

  def source_target?(frame)
    frame.size >= 3 &&
    frame[1].lines.first.match(/^\s+"target"/) &&
    frame[2].lines.first.match(/^\s+"source"/)
  end

  def should_reduce?(frame)
    single_element?(frame) || source_target?(frame)
  end

  def reduce_whitespace(frame)
    last = frame.size - 1
    frame[0] = frame[0].rstrip + ' '
    (1...last).each do |n|
      frame[n] = frame[n].strip + ' '
    end
    frame[last] = frame[last].lstrip
  end

end

# @api private
class ResourceDefinitionSorter

  class Path < Struct.new(:pattern, :keys); end

  def self.p(pattern, keys = [])
    Path.new('^' + pattern + '$', keys)
  end

  source = %w(target source name path value)
  param_source = source
  identifier_source = source

  PATHS = [
    p('', %w(service resources)),
    p('service', %w(actions has hasMany)),
    p('service/(actions|has|hasMany)'),
    p('service/(actions|has|hasMany)/\w+', %w(request resource)),
    p('service/(actions|has|hasMany)/\w+/request', %w(operation params)),
    p('service/(actions|has|hasMany)/\w+/request/params', param_source),
    p('service/(actions|has|hasMany)/\w+/resource', %w(type identifiers path)),
    p('service/(actions|has|hasMany)/\w+/resource/identifiers', identifier_source),
    p('resources'),
    p('resources/\w+', %w(identifiers shape load actions batchActions waiters has hasMany subResources)),
    p('resources/\w+/identifiers', %w(name type memberName)),
    p('resources/\w+/load', %w(request path)),
    p('resources/\w+/load/request', %w(operation params)),
    p('resources/\w+/load/request/params', param_source),
    p('resources/\w+/(actions|batchActions|has|hasMany)'),
    p('resources/\w+/(actions|batchActions|has|hasMany)/\w+', %w(request resource)),
    p('resources/\w+/(actions|batchActions|has|hasMany)/\w+/request', %w(operation params)),
    p('resources/\w+/(actions|batchActions|has|hasMany)/\w+/request/params', param_source),
    p('resources/\w+/(actions|batchActions|has|hasMany)/\w+/resource', %w(type identifiers path)),
    p('resources/\w+/(actions|batchActions|has|hasMany)/\w+/resource/identifiers', identifier_source),
    p('resources/\w+/waiters'),
    p('resources/\w+/waiters/\w+', %w(waiterName params path)),
    p('resources/\w+/waiters/\w+/params', param_source),
    p('resources/\w+/subResources', %w(resources identifiers)),
    p('resources/\w+/subResources/resources'),
    p('resources/\w+/subResources/identifiers'),
  ]

  def sort(obj, context = [])
    case obj
    when Hash then sort_hash(obj, context)
    when Array then obj.map { |v| sort(v, context) }
    else obj
    end
  end

  private

  def sort_hash(hash, context)
    path = context.join('/')
    if p = PATHS.find { |p| path.match(p.pattern) }
      sort_keys(p.keys, hash.keys, path).each.with_object({}) do |key, sorted|
        sorted[key] = sort(hash[key], context + [key])
      end
    else
      raise "PATH NOT MATCHED: #{path}"
    end
  end

  def sort_keys(key_order, keys, path)
    if key_order.empty?
      keys.sort
    else
      keys.sort_by do |key|
        if indx = key_order.index(key)
          indx
        else
          raise ArgumentError, "missing order for #{key.inspect} at #{path}"
        end
      end
    end
  end

end
