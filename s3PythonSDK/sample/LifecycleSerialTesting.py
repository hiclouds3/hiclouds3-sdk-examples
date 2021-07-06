import boto.exception 
import boto.s3.lifecycle
from client import conn 
from xml.dom import minidom

# test 1.Basic put bucket
#      2.put lifecycle rules(expire in days & on date)
#      3.get lifecycle
#      4.delete lifecycle
#      5.delete bucket 
def main(arg):
    try:
        bucket=conn.create_bucket(arg[0])
        
        expire=boto.s3.lifecycle.Expiration(date='2020-10-17T00:00:00.000Z')
        lifecycle_config = boto.s3.lifecycle.Lifecycle()
        lifecycle_config.add_rule('testLC1', '/', 'Enabled',1)
        lifecycle_config.add_rule('testLC2', 'test.txt', 'Enabled',expire)
        
        bucket.configure_lifecycle(lifecycle_config)
        result=bucket.get_lifecycle_config()
    #     for rules in result:
    #         print "Rule ID: "+rules.id
    #         print "prefix: "+rules.prefix
    #         print "Expiration: "+repr(rules.expiration)
    #     
    #     print "Clean up.."
        bucket.delete_lifecycle_configuration()
        conn.delete_bucket(bucket)
    #     print " - Lifecycle Serial Test Done ! \n"
    except boto.exception.S3ResponseError, e:
        xmldoc = minidom.parseString(e.body)
        itemlist = xmldoc.getElementsByTagName('Message')
        print "Status Code: " + repr(e.status)
        print "Reason: " + repr(e.reason)
        print "Message: " + itemlist[0].childNodes[0].nodeValue
    