import boto
import boto.exception 
from client import conn 
from xml.dom import minidom

# test 1.Basic Initiate MPU
#      2.Upload part from file
#      3.Complete MPU
#      4.Abort MPU
#      5.Copy part from Object
#      6.Basic list MPU & with parameter
#      7.Basci list parts & with parameters
def main(arg, filePath):
    try:
        bucket=conn.create_bucket(arg[1])
          
        #Basic MPU 
        #print 'Initiate MPU for castle.jpg'
        mpu=bucket.initiate_multipart_upload('castle.jpg',headers={"Content-Encoding":"UTF-8"},metadata={"flower":"lily"},policy="public-read")
         
        #print "uploading part1"
        fp = open(filePath[0], 'rb')
        mpu.upload_part_from_file(fp,1)
        fp.close()
         
        #print "uploading part2"
        fp = open(filePath[1], 'rb')
        mpu.upload_part_from_file(fp,2)
        fp.close()
         
        #print "uploading part3"
        fp = open(filePath[2], 'rb')
        mpu.upload_part_from_file(fp,3)
        fp.close()
         
        result=mpu.get_all_parts()
        #print "MPU for "+mpu.key_name+":"
    #     for m in result:
    #         print " - "+mpu.key_name+".part"+repr(p.part_number)
         
        #print "Complete MPU.."
        mpu.complete_upload()
         
        #print "Show Object & Clean up.."
        for k in bucket.list():
            #print k.name
            k.delete()
        #print "Basic MPU test done\n-------------------------------------------------"
         
        #Abort MPU
        #print "Test Abort MPU"
        mpu=bucket.initiate_multipart_upload('testAbort.jpg')
        result=bucket.get_all_multipart_uploads()
    #     for m in bucket.get_all_multipart_uploads():
    #         print m.bucket.name+":"+m.key_name+"\nVersionID: "+m.id 
        mpu.cancel_upload()
         
        #print "Basic Abort MPU test done\n-------------------------------------------------"
      
        #Copy parts from file
        #print "\nTest copy part from Obj"
        key=bucket.new_key('test1.txt')
        key.set_contents_from_string('Hello World!')

        mpu=bucket.initiate_multipart_upload('test2.txt')
        mpu2=bucket.initiate_multipart_upload('test3.txt')
        
        #copy part with range 
        #You can copy a range only if the source object is greater than 5 GB.
        #Range Copy Example: mpu.copy_part_from_key(arg[0],'5GB',1,0,5)
        #mpu.copy_part_from_key(arg[0],'5GB',1) 
        mpu.copy_part_from_key(arg[1],'test1.txt',2)
        mpu.copy_part_from_key(arg[1],'test1.txt',3)
        mpu2.copy_part_from_key(arg[1],'test1.txt',2)
        
    #     print "\nget MPU with max_uploads=2:"
        result=bucket.get_all_multipart_uploads(max_uploads=2)
    #     for m in result:
    #         print m.bucket.name+":"+m.key_name+"\nUploadID: "+m.id
        
    #     print "\nget MPU with key_marker=test2.txt:"
        result=bucket.get_all_multipart_uploads(key_marker='test2.txt')
    #     for m in result:
    #         print m.bucket.name+":"+m.key_name+"\nUploadID: "+m.id
        
        result=bucket.list_multipart_uploads(key_marker='test2.txt')
    #     for m in result:
    #         print m.bucket.name+":"+m.key_name+"\nUploadID: "+m.id
        
        
    #     print "\nGet part with max_parts=2 :"
        result=mpu.get_all_parts(max_parts=2)
    #     for p in result:
    #         print " - "+mpu.key_name+".part"+repr(p.part_number)
            
    #     print "Get part with part_number_marker=2:"
        result=mpu.get_all_parts(part_number_marker=2)
    #     for p in result:
    #         print " - "+mpu.key_name+".part"+repr(p.part_number)
        
        
    #     print "\nClean up.."
        # clear bucket
        result = bucket.get_all_versions()
        for v in result:
            v.delete()
        conn.delete_bucket(bucket)
    #     print " - MPU Serial Test Done!\n"
        
    except boto.exception.S3ResponseError, e:
        xmldoc = minidom.parseString(e.body)
        itemlist = xmldoc.getElementsByTagName('Message')
        print "Status Code: " + repr(e.status)
        print "Reason: " + repr(e.reason)
        print "Message: " + itemlist[0].childNodes[0].nodeValue
    