from botocore.exceptions import ClientError
from client import client
#from boto.s3.cors import CORSConfiguration

 
# test 1. Basic putBucket
#      2. put BucketACL
#      3. put BucketLogging (put log Native & to target bucket)
#      4. get BucketLogging
#      5. Delete Bucket
def main(arg):
    try:
        bucket = conn.create_bucket(arg[0])
        
        # Configure Bucket Cors Rule & PutBucketCORS
        #print "SetBucketCORS"
        cors_cfg = CORSConfiguration()
        cors_cfg.add_rule(['PUT', 'DELETE'], 'https://www.example.com', allowed_header='*', max_age_seconds=3000, expose_header='x-amz-server-side-encryption')
        cors_cfg.add_rule('GET', 'HEAD')
        bucket.set_cors(cors_cfg)
        
        # GetBucketCORS
        #print "GetBucketCORS"
        get_cors_cfg = bucket.get_cors()   
        #for rule in get_cors_cfg:
            #print "AllowedMethod: "+rule.to_xml()

         
        # DeleteBucketCORS
        #print "DeleteBucketCORS"
        bucket.delete_cors()
        
        #print "Clean up.."
        conn.delete_bucket(bucket)
        #print " - Bucket logging Serial test done!"
    except ClientError as e:
        print("Error operation : " + e.operation_name)
        print("Error response : " + e.response['Error']['Message'])