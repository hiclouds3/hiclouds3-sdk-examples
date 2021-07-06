import boto
from boto.exception import S3ResponseError
from client import conn 
from boto.s3.key import Key
from xml.dom import minidom
def main(arg, userAInfo, ownerInfo):
    try:
        bucket = conn.create_bucket(arg[0])
        bucket.set_canned_acl('public-read')
        result = repr(bucket.get_acl())
        #print "Set canned ACL 'public-read':\n" + result
        
        bucket.make_public();
        result = repr(bucket.get_acl())
        #print "Make bucket public :\n" + result
        
        bucket.set_canned_acl('private')
        
        bucket.add_email_grant('READ', userAInfo[1],True)
        bucket.add_user_grant('FULL_CONTROL', ownerInfo[0],True)
        result = repr(bucket.get_acl())
        #print "Add grant by email & by user id:\n" + result
        
        #print "\nEnable Versioning :"
        bucket.configure_versioning(True)
        #print bucket.get_versioning_status()
        
        k = Key(bucket)
        k.key = 'testV.txt'
        k.set_contents_from_string('This is a test2 of S3')
        
        result = bucket.get_all_versions()
        
        #print "\nList all Versions:\n  File    |   Version-ID"
        #for v in result:
            #print v.name + " | " + v.version_id
        
        #print "\nSuspend Versioning :"
        bucket.configure_versioning(False)
        #print bucket.get_versioning_status()
        
        #print "\nClean up.."
        # clear bucket
        
        #for g in bucket.list_grants():
        #    print g.display_name
        
        for v in bucket.get_all_versions():
            v.delete()
        
        conn.delete_bucket(bucket)
        
        #print "ACL Serial Test done!"
        
    except S3ResponseError, e:
        xmldoc = minidom.parseString(e.body)
        itemlist = xmldoc.getElementsByTagName('Message')
        print "Status Code: " + repr(e.status)
        print "Reason: " + repr(e.reason)
        print "Message: " + itemlist[0].childNodes[0].nodeValue
