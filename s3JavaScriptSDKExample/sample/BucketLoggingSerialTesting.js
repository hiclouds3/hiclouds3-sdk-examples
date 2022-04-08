// 1. set ACL (grant LogDelivery Permission write and WRITE_ACP)
// 2. put bucket Logging
// 3. get bucket Logging

function bucketLoggingTest (){
	return Q.fcall(function (){
		var deferred = Q.defer();
		console.log('-----------------------------------------');
		console.log('bucketLoggingTest begin!');
		deferred.resolve();
		return deferred.promise;
	})
	.then(function(){
		var params = {
				  Bucket: testBucketName, /* required */
				  //ACL: 'public-read', //private | public-read | public-read-write | authenticated-read',
				  AccessControlPolicy: {
				    Grants: [
				      {
				        Grantee: {
				          Type: 'Group', /* required */
				          URI: 'http://acs.amazonaws.com/groups/s3/LogDelivery'
				        },
				        Permission: 'WRITE_ACP',//'FULL_CONTROL | WRITE | WRITE_ACP | READ | READ_ACP'
				      },
				      {
				        Grantee: {
				          Type: 'Group', /* required */
				          URI: 'http://acs.amazonaws.com/groups/s3/LogDelivery'
				        },
				        Permission: 'WRITE'//'FULL_CONTROL | WRITE | WRITE_ACP | READ | READ_ACP'
				      },
				      {
				        Grantee: {
				          Type: 'Group', /* required */
				          URI: 'http://acs.amazonaws.com/groups/s3/LogDelivery'
				        },
				        Permission: 'READ_ACP'//'FULL_CONTROL | WRITE | WRITE_ACP | READ | READ_ACP'
				      },
						{
							Grantee: {
								Type: 'CanonicalUser', /* required */
								ID: ownerID
							},
							Permission: 'FULL_CONTROL',//'FULL_CONTROL | WRITE | WRITE_ACP | READ | READ_ACP'
						}
				       // more items 
				    ],
				    Owner: {
				      DisplayName: ownerName,
				      ID: ownerID
				    }
				  },
				};
		return putBucketAcl(params, s3);
	})
	.then(function(){
		var params = {
				  Bucket: testBucketName /* required */
				};
		return getBucketLogging(params, s3);
	})
	.then(function(){
		var params = {
				  Bucket: testBucketName, /* required */
				  BucketLoggingStatus: { /* required */
				    LoggingEnabled: {
				      TargetBucket: testBucketName,
				      TargetGrants: [
				        {
				          Grantee: {
				            Type: 'CanonicalUser', /* required */
				            // DisplayName: 'STRING_VALUE',
				            // EmailAddress: 'STRING_VALUE',
				            ID: granteeId,
				            // URI: ''
				          },
				          Permission: 'READ'//FULL_CONTROL | READ | WRITE
				        },
						  {
							  Grantee: {
								  Type: 'CanonicalUser', /* required */
								  ID: ownerID
							  },
							  Permission: 'FULL_CONTROL',//'FULL_CONTROL | WRITE | WRITE_ACP | READ | READ_ACP'
						  }
				        /* more items */
				      ],
				      TargetPrefix: 'logs/'
				    }
				  },
				  // ContentMD5: 'STRING_VALUE'
				};
		return putBucketLogging(params, s3);
	})
	.then(function(){
		var params = {
				  Bucket: testBucketName /* required */
				};
		return getBucketLogging(params, s3);
	});

}


function putBucketLogging(params, s3){
	var deferred = Q.defer();

	s3.putBucketLogging(params, function(err, data) {
		  if (err) console.log(err, err.stack); // an error occurred
		  else     {
		  	console.log(data);
		  	console.log('put bucket logging succeed');
			deferred.resolve(data);
		  }          
		});
	return deferred.promise;
}

function getBucketLogging(params, s3){
	var deferred = Q.defer();

	s3.getBucketLogging(params, function(err, data) {
		  if (err) console.log(err, err.stack); // an error occurred
		  else     {
		  	console.log(data);
			deferred.resolve(data);
		  }           // successful response
		});
	return deferred.promise;

}
