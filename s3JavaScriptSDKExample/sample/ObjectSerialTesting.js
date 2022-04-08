
function objectSerialTesting (){
	return Q.fcall(function f1()
	{
		var deferred = Q.defer();
		console.log('------------------------------------------');
		console.log('object serial Testing begin!');
		deferred.resolve();
		return deferred.promise;
	})
	.then(function f3()
	{
		 putObject (s3, testBucketName, 'objectkey1', 'object_data_1');
	})
	.then(function f3()
	{
		 putObject (s3, testBucketName, 'objectkey2', 'object_data_2');
	})
	.then(function f3()
	{
		 putObject (s3, testBucketName, 'objectkey3', 'object_data_3');
	})
	.then(function f2()
	{
		var deferred = Q.defer();
		var params = {
			  Bucket: testBucketName, /* required */
			  Delete: { /* required */
			    Objects: [ /* required */
			      {
			        Key: 'objectkey1', /* required */
			      },
			      {
			        Key: 'objectkey3', /* required */
			      },
			      /* more items */
			    ],
		  	  },
		};
		s3.deleteObjects(params, function(err, data) {
		  if (err) console.log(err, err.stack); // an error occurred
		  else {
			  console.log('delete object 1 and object 3');
			  console.log(data);           
			  deferred.resolve(data);
		  }
		});
		return deferred.promise;
	})	
	.then(function f3()
	{
		return listObject(s3, testBucketName);
	})
	.then(function f4()
	{
		return deleteAllObjects(s3, testBucketName);
	});
}


