import sys
import boto.exception
from client import conn 
from xml.dom import minidom

# test 1.Basic put bucket
#      2.put Object with header & custom metadata
#      3.head object
#      4.get object
#      5.copy object (with preserved ACL)  

def percent_cb(complete, total):
    sys.stdout.write('.')
    sys.stdout.flush()

def main(arg):
    try:
        #boto.config.add_section('Boto')
        #boto.config.set('Boto', 'debug', '2')
        content="abcdefghijklmnopqrstuvwxyz<br>01234567890112345678901234<br>!@#$%^&*()-=[]{};':',.<>/?<br>01234567890112345678901234<br>abcdefghijklmnopqrstuvwxyz<br>"
        
        bucket=conn.create_bucket(arg[0])
        bucket2=conn.create_bucket(arg[1])
        
    #     print "Put Object 'test.txt' with Headers & custom metadata.."
        
        key=bucket.new_key('test.txt')
        key.set_contents_from_string(content,{"Content-Disposition":"attachment; filename=\"default.txt\"","Content-Encoding":"UTF-8","Content-Type":"text/plain","Cache-Control":"no-cache","Content-MD5":"OWE4YmRmZTA1NzlhMmJmZTBiNDMyY2Y5MTRmZDY4ODk=",'x-amz-meta-flower':'lily', 'x-amz-meta-color':'pink'},True)
        key.set_canned_acl('public-read')
        
        key=bucket.lookup('test.txt')
    #     print "Cache-Control: "+key.cache_control
    #     print "Content-Type"+key.content_type
    #     print "Content-Encoding: "+key.content_encoding 
    #     print "Content-Disposition: "+key.content_disposition
    #     print "Metadata: "+repr(key.metadata)
         
    #     print "\nCopy chttest6://test.txt to chttest7 as 'cptest' and preserve_acl.."
        url= key.generate_url(120,'POST',headers={"testurl":"test.txt"},force_http=True,response_headers={"Content-Type":"text/plain"},expires_in_absolute=True,policy='public-read',version_id=key.version_id)
    #     print url
        #copy file with origin ACL
        #key2=key.copy('chttest7','cptest',preserve_acl=True)
        key2=key.copy(arg[1],'cptest',preserve_acl=True)
    #     print "ACL:\n"+repr(key2.get_acl())
        content_string=key2.get_contents_as_string(cb=percent_cb,num_cb=10,headers={"testdown":"load"},response_headers={"response-content-type":"text/plain"}) #,response_headers={"Content-Type":"text/plain"}
        #print "\nGet file content as string..\n    "+content_string
        
        
        fp = open('download.test', 'w')
        key.get_contents_to_file(fp,cb=percent_cb,num_cb=10,headers={"testdown":"load"},response_headers={"response-content-type":"text/plain"}) #,response_headers={"Content-Type":"text/plain"}
        key.get_contents_to_filename('download.test2',cb=percent_cb,num_cb=10,headers={"testdown":"load"},response_headers={"response-content-type":"text/plain"}) #,response_headers={"Content-Type":"text/plain"}
        key.get_file(fp,cb=percent_cb,num_cb=10,headers={"testdown":"load"},override_num_retries=2,response_headers={"response-content-type":"text/plain"}) #,response_headers={"Content-Type":"text/plain"}
       
        f2 = open('testwrite', 'w+')
        f2.write("Hello")
        key.set_contents_from_file(f2,replace=True,cb=percent_cb,num_cb=2,rewind=True,policy='public-read')
        f2.close()
        key.set_contents_from_filename('download.test2',replace=True,cb=percent_cb,num_cb=2,policy='public-read')
    #     print key.get_redirect()    
    #     print "\nClean up.."
        #clear bucket
        for v in bucket.get_all_versions():
            v.delete()
        for v in bucket2.get_all_versions():
            v.delete()
        conn.delete_bucket(bucket)
        conn.delete_bucket(bucket2)
           
         
    #     print " - Object Serial Test done!"
    except boto.exception.S3ResponseError, e:
        xmldoc = minidom.parseString(e.body)
        itemlist = xmldoc.getElementsByTagName('Message')
        print "Status Code: " + repr(e.status)
        print "Reason: " + repr(e.reason)
        print "Message: " + itemlist[0].childNodes[0].nodeValue
    