//1. put bucket ACL for canned public-read
//2. get bucket ACL

function aclSerialTest (){

	return Q.fcall(function(){
	var deferred = Q.defer();
	console.log('-----------------------------------------');
	console.log('testing acl begin!');
	deferred.resolve();
	return deferred.promise;
	})
	.then(function (){
		var params = {
				  Bucket: testBucketName, /* required */
				  ACL: 'public-read', //private | public-read | public-read-write | authenticated-read',
				};
				return putBucketAcl(params, s3);
	})
	.then(function (){
		return getBucketAcl (testBucketName,s3);
	})
	.then(function(){
		var params = {
				  Bucket: testBucketName, /* required */
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
					  },
				    ],
				    Owner: {
				      DisplayName: ownerName,
				      ID: ownerID
				    }
				  },
				};
		return putBucketAcl(params, s3);
	})
	.then(function (){
		return getBucketAcl (testBucketName,s3);
	})
	.then(function (){
	var params = {
			Bucket: testBucketName,
			Key: 'objectkey1',
			ACL: 'public-read',
			Body: 'object_data_1',
			ContentType :'text',
			}
	return s3Perform.putObject(params, s3);
	})
	.then(function (){
		var params = {
		  Bucket: testBucketName, /* required */
		  Key: 'objectkey1', /* required */
		  AccessControlPolicy: {
		    Grants: [
		      {
		        Grantee: {
		          Type: 'CanonicalUser',
		          ID: granteeId,
		        },
		        Permission: 'FULL_CONTROL'
		      },
				{
					Grantee: {
						Type: 'CanonicalUser', /* required */
						ID: ownerID
					},
					Permission: 'FULL_CONTROL',//'FULL_CONTROL | WRITE | WRITE_ACP | READ | READ_ACP'
				}
		    ],
		    Owner: {
		      DisplayName: ownerName,
		      ID: ownerID
		    }
		  },
		};
		return putObjectAcl(params, s3);
	})
	.then(function (){
		var params = {
				  Bucket: testBucketName, /* required */
				  Key: 'objectkey1',
		};
		return getObjectAcl(params, s3);
	})
	.then(function(){
		return deleteAllObjects(s3, testBucketName);
	});
}


function putBucketAcl(params, s3)
{
	var deferred = Q.defer();
	s3.putBucketAcl(params, function(err, data) {
		  if (err) console.log(err, err.stack); // an error occurred
		  else {
		  	console.log('put bucket acl succeed');
		  	deferred.resolve(data);
		  }           // successful response
	});
	return deferred.promise;
}

function getBucketAcl (BucketName,s3){
	var deferred = Q.defer();
	var params = {
	  Bucket: BucketName /* required */
	};
	s3.getBucketAcl(params, function(err, data) {
	  if (err) console.log(err, err.stack); // an error occurred
	  else	{
		  console.log('get bucket acl succeed');
		  console.log(data);
		  deferred.resolve(data);
	  }
	});
	return deferred.promise;	
}

function getObjectAcl (params, s3)
{
	var deferred = Q.defer();
	s3.getObjectAcl(params, function(err, data) {
		  if (err) {
			  console.log(err, err.stack);
			  deferred.resolve();
		  }// an error occurred
		  else     {
			  console.log('get object acl succeed');
			  console.log(data);
			  deferred.resolve(data);// successful response
		  }
		});
	return deferred.promise;	
}


function putObjectAcl(params, s3)
{
	var deferred = Q.defer();
	s3.putObjectAcl(params, function(err, data) {
		  if (err) {
			  console.log(err, err.stack);
			  deferred.resolve();
		  }
		  else     {
			  console.log('put object acl succeed');
			  console.log(data);
			  deferred.resolve(data);		  
		  }
		});
	return deferred.promise;	

}