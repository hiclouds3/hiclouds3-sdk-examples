import boto3
from boto3.exceptions import S3UploadFailedError
from client import client 
#from boto3.s3.key import Key
from xml.dom import minidom
def Expectexception(e,code):
    if(e.status != code):
        print("Expected Status Code :"+repr(code)+", get another Exception...")
        xmldoc = minidom.parseString(e.body)
        itemlist = xmldoc.getElementsByTagName('Message')
        print("Status Code: " + repr(e.status))
        print("Reason: " + repr(e.reason))
        print("Message: " + itemlist[0].childNodes[0].nodeValue)
def main(arg):
    
    for i in arg:
        try:
            print( "prepare clean bucket: " + i)
            result =client.list_objects_v2(
                    Bucket=i,
                    )
            for v in result:
                if v=='Contents':
                    for r in result['Contents']:
                        print(r['Key'])
                        client.delete_object(
                            Bucket=i,
                            Key=r['Key']
                        )
            
        except S3UploadFailedError as e:
            Expectexception(e,404)
#except S3ResponseError,e:
#Expectexception(e,404)
