import boto
from boto.exception import S3ResponseError
from client import conn 
from boto.s3.key import Key
from boto.s3.acl import Grant
from xml.dom import minidom

 
# test 1. Basic putBucket
#      2. put BucketACL
#      3. put BucketLogging (put log Native & to target bucket)
#      4. get BucketLogging
#      5. Delete Bucket
def main(arg, ownerInfo):
    try:
        bucket = conn.create_bucket(arg[0])
        bucket2 = conn.create_bucket(arg[1])
        
        # the following both way can give log-delievery group WRITE & READ_ACP permission
        bucket.set_canned_acl('log-delivery-write')
        bucket2.set_as_logging_target()
        bucket.add_email_grant('READ', ownerInfo[1],True)
        bucket.enable_logging(bucket,"chttestlog",grants=bucket.list_grants())
        #print "Put log to itself & give full permission by user email:\n"+repr(bucket.get_logging_status())
        bucket.enable_logging(bucket2,"chttestlog")
        #print "Put log to another bucket:\n"+repr(bucket.get_logging_status())
        bucket.disable_logging()
        #print "Disable logging:\n"+repr(bucket.get_logging_status())
        
        #print "Clean up.."
        conn.delete_bucket(bucket)
        conn.delete_bucket(bucket2)
        #print " - Bucket logging Serial test done!"
    except S3ResponseError, e:
        xmldoc = minidom.parseString(e.body)
        itemlist = xmldoc.getElementsByTagName('Message')
        print "Status Code: " + repr(e.status)
        print "Reason: " + repr(e.reason)
        print "Message: " + itemlist[0].childNodes[0].nodeValue