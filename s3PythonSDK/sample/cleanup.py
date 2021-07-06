import boto
from boto.exception import S3ResponseError
from client import conn 
from boto.s3.key import Key
from xml.dom import minidom
def Expectexception(e,code):
    if(e.status != code):
        print"Expected Status Code :"+repr(code)+", get another Exception...";
        xmldoc = minidom.parseString(e.body)
        itemlist = xmldoc.getElementsByTagName('Message')
        print "Status Code: " + repr(e.status)
        print "Reason: " + repr(e.reason)
        print "Message: " + itemlist[0].childNodes[0].nodeValue    
def main(arg):
    
    for i in arg:
        try:
            print "prepare clean bucket: " + i
            bucket = conn.get_bucket(i)
            bucket.configure_versioning(False)

            result = bucket.get_all_versions()
            for v in result:
                bucket.delete_key(v.name,version_id=v.version_id)
        
            conn.delete_bucket(bucket)
            
        except S3ResponseError,e:
            Expectexception(e,404)
