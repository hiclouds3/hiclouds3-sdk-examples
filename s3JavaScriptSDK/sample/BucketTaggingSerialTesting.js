

function bucketTaggingSerialTesting (){
	return Q.fcall(function ()
	{
		var deferred = Q.defer();
		console.log('-----------------------------------------');
		console.log('bucketTaggingSerialTesting begins!');
		deferred.resolve();
		return deferred.promise;
	})
	.then(function(){
		var params = {
		  Bucket: testBucketName, /* required */
		  Tagging: { /* required */
		    TagSet: [ /* required */
		      {
		        Key: 'key_VALUE', /* required */
		        Value: 'VALUE' /* required */
		      },
		    ]
		  },
		};
		return putBucketTagging(params, s3);
	})
	.then(function(){
		var params = {
				  Bucket: testBucketName /* required */
				};
		return getBucketTagging(params, s3);
	})
	.then(function(){
		var params = {
				  Bucket: testBucketName /* required */
				};
		return deleteBucketTagging(params, s3);
	});
	
}


function getBucketTagging(params, s3)
{
	var deferred = Q.defer();
	s3.getBucketTagging(params, function(err, data) {
		  if (err) {
			  console.log(err, err.stack); 
			  deferred.resolve();
		  }// an error occurred
		  else     {
			console.log('get bucket Tagging succeed');
		  	console.log(data);
			deferred.resolve(data);
		  }
	});
	return deferred.promise;
}

function putBucketTagging(params, s3)
{
	var deferred = Q.defer();
	s3.putBucketTagging(params, function(err, data) {
		if (err) {
			console.log(err, err.stack);
			deferred.resolve();
		  }// an error occurred
		else     {
			console.log('put bucket Tagging succeed');
			console.log(data);
			deferred.resolve(data);
		}
	});
	return deferred.promise;
}


function deleteBucketTagging(params, s3)
{
	var deferred = Q.defer();
	s3.deleteBucketTagging(params, function(err, data) {
	  if (err) {
		  console.log(err, err.stack);
		  deferred.resolve();
	  }// an error occurred
	  else     {
	  	console.log('delete bucketTagging succeed');
		deferred.resolve(data);
	  }
	});
	return deferred.promise;
}

