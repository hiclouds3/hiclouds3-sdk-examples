
function versionTesting (){  
  var versions = new Array(3);
  var params = {
    Bucket: testBucketName, /* required */
    VersioningConfiguration: { /* required */
      MFADelete: 'Disabled',//'Enabled | Disabled'
      Status: 'Enabled'//Enabled | Suspended
    },
    // ContentMD5: 'STRING_VALUE',
    // MFA: 'STRING_VALUE'
  };
  s3.putBucketVersioning(params, function(err, data) {
    if (err) console.log(err, err.stack); // an error occurred
    else     {
      console.log(data);
      var params = {
        Bucket: testBucketName /* required */
      };
      s3.getBucketVersioning(params, function(err, data) {
        if (err) console.log(err, err.stack); // an error occurred
        else     console.log(data);           // successful response
      });
      putObject(s3, testBucketName, 'objectkey', 'object_data_1');
      putObject(s3, testBucketName, 'objectkey', 'object_data_2');
      putObject(s3, testBucketName, 'objectkey', 'object_data_3');
    }           // successful response
  }).on('success', function (){
    console.log('put 3 version object complete');
    var params = {
    Bucket: testBucketName, /* required */
    };
    s3.listObjectVersions(params, function(err, data) {
      if (err) console.log(err, err.stack); // an error occurred
      else     {
        console.log(data);
        versions = data.Versions;
        console.log(versions[0].VersionId);
        console.log(versions[1].VersionId);
        getObject(s3, testBucketName, 'objectkey'); //latest version
        // getObjectWithVersion('testrubybucket1', 'objectkey',versions[0].VersionId); only work in IE
        // getObjectWithVersion('testrubybucket1', 'objectkey',versions[1].VersionId); only work in IE
      }           // successful response
    });
  }).send();

}




function getObjectWithVersion(BucketName, Key, VersionId)
{
	var params = {
	Bucket: BucketName,
	Key: Key,
	VersionID: VersionId,

	}
	s3.getObject(params, function(err, data){
		if (err) 
			console.log(err,err.stack);
		else 
			console.log(data.Body);
	});

}


