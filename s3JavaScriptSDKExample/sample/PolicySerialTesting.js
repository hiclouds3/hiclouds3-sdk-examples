

function policySerialTesting (){
	return Q.fcall(function (){
		var deferred = Q.defer();
		console.log('------------------------------------------');
		console.log('policySerialTesting Testing begin!');
		deferred.resolve();
		return deferred.promise;
	})
	.then(function(){
		var Json_myPolicy = '{"Id": "Policy1436779373666","Version": "2012-10-17","Statement": [{"Sid": "Stmt1436779368306","Action": ["s3:GetObject"],"Effect": "Deny","Resource": "arn:aws:s3:::'+testBucketName+'/*","Principal": "*"}]}';
		var params = {
				  Bucket: testBucketName, /* required */
				  Policy: Json_myPolicy, /* required */
				};
		return putBucketPolicy(params, s3);
	})
	.then(function(){
		var params = {
				  Bucket: testBucketName /* required */
				};
		return getBucketPolicy (params,s3);
	})
	.then(function(){
		var params = {
				  Bucket: testBucketName /* required */
				};
		return deleteBucketPolicy (params,s3);
	});
}

function putBucketPolicy(params, s3){
	var deferred = Q.defer();

	s3.putBucketPolicy(params, function(err, data) {
		  if (err) console.log(err, err.stack); // an error occurred
		  else     {
		  	console.log(data);
		  	deferred.resolve();
		  }           // successful response
		});
	return deferred.promise;

}



function deleteBucketPolicy (params,s3){
	var deferred = Q.defer();
	s3.deleteBucketPolicy(params, function(err, data) {
	  if (err) console.log(err, err.stack); // an error occurred
	  else     {
		  console.log(data);           
		  deferred.resolve();
	  }
	});
	return deferred.promise;
}

function getBucketPolicy (params,s3){
	var deferred = Q.defer();

	s3.getBucketPolicy(params, function(err, data) {
	  if (err) console.log(err, err.stack); // an error occurred
	  else     {
		  console.log(data);
		  deferred.resolve();
	  }           // successful response
	});		
	return deferred.promise;
}



// function deletePolicy (bucketName){
// 	var params = {
// 	  Bucket: bucketName /* required */
// 	};
// 	s3.deleteBucketPolicy(params, function(err, data) {
// 	  if (err) console.log(err, err.stack); // an error occurred
// 	  else     console.log(data);           // successful response
// 	});
// }

// function getAndDetletePolicy (bucketName){
// 	var params = {
//   		Bucket: bucketName /* required */
// 	};
// 	s3.getBucketPolicy(params, function(err, data) {
// 	  if (err) console.log(err, err.stack); // an error occurred
// 	  else     {
// 		  console.log(data);
// 		  deletePolicy (bucketName)
// 	  }           // successful response
// 	});
// }

// function putAndGetAndDeletePolicy (bucketName,myPolicy){
// 	var params = {
// 	  Bucket: bucketName, /* required */
// 	  Policy: myPolicy, /* required */
// 	  // ContentMD5: 'STRING_VALUE'
// 	};
// 	s3.putBucketPolicy(params, function(err, data) {
// 	  if (err) console.log(err, err.stack); // an error occurred
// 	  else     {
// 	  	console.log(data);
// 	  	getAndDetletePolicy (bucketName);
// 	  }           // successful response
// 	});
// }