from botocore.exceptions import ClientError
from client import client
# Use http://awspolicygen.s3.amazonaws.com/policygen.html to generate policy String
# test 1.Put bucket
#      2.Put policy
#      3.Get policy
#      4.Delete policy
def main(arg):
    try:
        bucket=conn.create_bucket(arg[0])
        policy='{"Version":"2012-10-17","Statement":[{"Sid":"DenyPublicREAD","Effect":"Deny","Principal":{"AWS":"*"},"Action":"s3:GetObject","Resource":"arn:aws:s3:::'+arg[0]+'/*"}]}';
        #print "Put Deny Public-read policy to bucket"
        bucket.set_policy(policy)
        #print "Get Policy:"
        #print repr(bucket.get_policy())
        bucket.delete_policy()
        
        #print "\n Clean up.."
        conn.delete_bucket(bucket)
        #print " - Policy Serial Test Done !"
    except ClientError as e:
        print("Error operation : " + e.operation_name)
        print("Error response : " + e.response['Error']['Message'])
    