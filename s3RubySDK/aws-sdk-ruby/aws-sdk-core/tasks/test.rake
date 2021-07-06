require 'rspec/core/rake_task'

desc "aws-sdk-core unit tests"
RSpec::Core::RakeTask.new('test:unit:aws-sdk-core') do |t|
  t.rspec_opts = "-I #{$REPO_ROOT}/aws-sdk-core/lib"
  t.rspec_opts << " -I #{$REPO_ROOT}/aws-sdk-core/spec"
  t.pattern = "#{$REPO_ROOT}/aws-sdk-core/spec"
end
task 'test:unit' => 'test:unit:aws-sdk-core'

begin
  require 'cucumber/rake/task'
  desc = 'aws-sdk-core integration tests'
  Cucumber::Rake::Task.new('test:integration:aws-sdk-core', desc) do |t|
    t.cucumber_opts = 'aws-sdk-core/features -t ~@veryslow'
  end
  task 'test:integration' => 'test:integration:aws-sdk-core'
rescue LoadError
  desc 'aws-sdk-core integration tests'
  task 'test:integration' do
    puts 'skipping aws-sdk-core integration tests, cucumber not loaded'
  end
end
