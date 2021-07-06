import boto.exception 
from client import conn
from xml.dom import minidom

# test 1.
#      2.
#      3.
def main(arg):
    try:
        bucket=conn.create_bucket(arg[0])
        
        #print "\nEnable Versioning :"
        bucket.configure_versioning(True)
        #print bucket.get_versioning_status()
        
        k = bucket.new_key('/photo/cht2.jpg')
        k.set_contents_from_string('Hello',replace=True)
        k.set_contents_from_string('Hello Python',replace=True)
        k.set_contents_from_string('Hello Python World!',replace=True)
        k = bucket.new_key('cht1.jpg')
        k.set_contents_from_string('Hello',replace=True)
        k.set_contents_from_string('Hello Python',replace=True)
        k.set_contents_from_string('Hello Python World!',replace=True)
        
        result = bucket.get_all_versions()
        
        #print "\nList all Versions:\n  File         |   Version-ID"
        #for v in result:
            #print v.name + " | " + v.version_id
        
        result = bucket.get_all_versions(max_keys=2)
        
        #print "\nList Versions with max_keys=2:\n  File         |   Version-ID"
        #for v in result:
            #print v.name + " | " + v.version_id
        
        result = bucket.get_all_versions(delimiter='/')
        
        #print "\nList Versions with delemeter '/':\n  File         |   Version-ID"
        #for v in result:
            #print v.name + " | " + v.version_id
        
        result = bucket.get_all_versions(prefix='photo/')
        
        #print "\nList Versions with prefix 'photo/':\n  File         |   Version-ID"
        #for v in result:
            #print v.name + " | " + v.version_id
        
        result = bucket.get_all_versions(prefix='photo/',max_keys=2)
        
        #print "\nList Versions with prefix ='photo/' & max_keys=2:\n  File         |   Version-ID"
        #for v in result:
            #print v.name + " | " + v.version_id
        
        
        #print "\nSuspend Versioning :"
        bucket.configure_versioning(False)
        #print bucket.get_versioning_status()
        
        #print "\nClean up.."
        # clear bucket
        result = bucket.get_all_versions()
        for v in result:
            bucket.delete_key(v.name,version_id=v.version_id)
        
        conn.delete_bucket(bucket)
        #print " - Versioning Serial Test Done !"
        
    except boto.exception.S3ResponseError, e:
        xmldoc = minidom.parseString(e.body)
        itemlist = xmldoc.getElementsByTagName('Message')
        print "Status Code: " + repr(e.status)
        print "Reason: " + repr(e.reason)
        print "Message: " + itemlist[0].childNodes[0].nodeValue
    