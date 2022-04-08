//1. put lifecyle
//2. get lifecycle
//3. delete lifecycle

function lifecycleSerialTesting (){		
	
	return Q.fcall(function ()
	{
		var deferred = Q.defer();
		console.log('-----------------------------------------');
		console.log('testing life cycle begin!');
		deferred.resolve();
		return deferred.promise;

	})
	.then(function ()
	{
		var deferred = Q.defer();
		console.log('put lifecycle');
		var params = {
		  Bucket: testBucketName, /* required */
		  // ContentMD5: 'STRING_VALUE',
		  LifecycleConfiguration: {
		    Rules: [ /* required */
		      {
		        Prefix: '', /* required */
		        Status: 'Enabled', /* 'Enabled | Disabled' */
		        Expiration: {
		          // Date: new Date || 'Wed Dec 31 1969 16:00:00 GMT-0800 (PST)' || 123456789,
		          Days: 730
		        },
		        ID: 'life_cycle_rule',
		        Transition: {
		          // Date: new Date || 'Wed Dec 31 1969 16:00:00 GMT-0800 (PST)' || 123456789,
		          Days: 365,
		          StorageClass: 'GLACIER'
		        }
		      },
		    ]
		  }
		};
		return putBucketLifecycle(params, s3);
    })
    .then(function(){
		var params = {
		  Bucket: testBucketName /* required */
		};
    	return getBucketLifecycle(params, s3);
    })
    .then(function(){
		var params = {
		  Bucket: testBucketName /* required */
		};
    	return deleteLifeCycle (params,s3);
    });

}


function getBucketLifecycle(params, s3)
{
	var deferred = Q.defer();
	s3.getBucketLifecycle(params, function(err, data) {
		  if (err) console.log(err, err.stack); // an error occurred
		  else     {
		  	console.log(data);
		  	deferred.resolve(data);
		  }           // successful response
	})
	return deferred.promise;
}

function putBucketLifecycle(params, s3)
{
	var deferred = Q.defer();
	s3.putBucketLifecycle(params, function(err, data) {
		  if (err) console.log(err, err.stack); // an error occurred
		  else     {
		  	console.log(data);
		  	console.log("put life sycle success");
		  	deferred.resolve(data);
		  }
	})
	return deferred.promise;
}


function deleteLifeCycle (params,s3){
	var deferred = Q.defer();
	console.log('deletelifecycle');
	s3.deleteBucketLifecycle(params, function(err, data) {
	  if (err) console.log(err, err.stack); // an error occurred
	  else     {
		  console.log(data);
	  	  console.log("delete life sycle success");
		  deferred.resolve();
	  }
	});
	return deferred.promise;
}