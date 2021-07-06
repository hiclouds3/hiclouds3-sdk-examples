import boto
import boto.exception 
from boto.s3.key import Key
from boto.s3.connection import Location
from boto.s3.acl import CannedACLStrings
from client import conn 
from xml.dom import minidom

# test 1.Put bucket with region
#      2.Put Bucket with canned ACL
#      3.Basic putObject 
#      4.Get bucket with parameters
#      5.Basic delete Objects
#      6.Basic delete Bucket
def main(arg):
    try:
        #putBucket with region
        #print "Try putBucket with region 'USWest2':"
        bucket=conn.create_bucket(arg[2],location=Location.USWest2)
        #print "Bucket server location: "+bucket.get_location()
        conn.delete_bucket(bucket)
        #print " - Put bukcet with region done\n---------------------------------------------------"
        #print "put Bucket with 'public-read' permission.."
        bucket = conn.create_bucket(arg[0],policy='public-read')
        #print repr(bucket.get_acl())
        #url that expire in 120 secs
        #print bucket.generate_url(120)
        
        fileName = "apple.jpg"
        fileName2 = "photos/2006/January/sample.jpg"
        fileName3 = "photos/2006/February/sample2.jpg"
        fileName4 = "asset.txt"
        key = bucket.new_key(fileName)
        key.set_contents_from_string('Hello World!')
        
        key2 = bucket.new_key(fileName2)
        key2.set_contents_from_string('Hello World!')
        key2.set_canned_acl('public-read')
        
        key3 = bucket.copy_key(fileName3,bucket.name,fileName2,preserve_acl=True,headers={"Content-Disposition":"attachment; filename=\"default.txt\""})
        key3 = bucket.get_key(fileName3,response_headers={"response-content-type":"text/plain"})
        #print repr(key3.content_disposition)
        
        key4 = bucket.new_key(fileName4) 
        key4.set_contents_from_string('Hello World!')
        
        #print "Get bucket normally:"
    #     for key in bucket.list():
                #print " - "+key.name
        
        #print "Get bucket with prefix 'photos/':"
        result=bucket.list(prefix="photos/")
    #     for key in result:
    #         print " - "+key.name
        
        #print "Get bucket with delimiter '/':"
        result= bucket.list(delimiter="/")
    #     for key in result:
    #         print " - "+key.name
        
        #print "Get bucket with max_keys=2:"
        result= bucket.get_all_versions(max_keys=2)
    #     for key in result:
    #         print " - "+key.name
    #             
        #print "Get bucket with delimiter '/' & prefix 'photos/2006/:"
        result=bucket.list(prefix="photos/2006/",delimiter="/")
    #     for key in result:
    #         print " - "+key.name
    #     
        #print "Get bucket with marker 'apple.jpg':"
        result=bucket.list(marker='apple.jpg')
    #     for key in result:
    #         print " - "+key.name
       
        #print "\nClean up.."
        # clear bucket
        result = bucket.get_all_keys()
        bucket.delete_keys(result,quiet=True)
          
        conn.delete_bucket(bucket)
        #print " - Bucket Serial Test done!\n"
        
    except boto.exception.S3ResponseError, e:
        xmldoc = minidom.parseString(e.body)
        itemlist = xmldoc.getElementsByTagName('Message')
        print "Status Code: " + repr(e.status)
        print "Reason: " + repr(e.reason)
        print "Message: " + itemlist[0].childNodes[0].nodeValue
    