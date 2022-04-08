function websiteSerialTesting (){


	return Q.fcall(function (){
		var deferred = Q.defer();
		console.log('-----------------------------------------');
		console.log('websiteSerialTesting begin!');
		deferred.resolve();
		return deferred.promise;
	})
	.then(function(){
		var params = {
				  Bucket: testBucketName, /* required */
				  WebsiteConfiguration: { /* required */
				    ErrorDocument: {
				      Key: 'error.html' /* required */
				    },
				    IndexDocument: {
				      Suffix: 'index.html' /* required */
				    },
				  },
				};
		return putBucketWebsite(params, s3);
	})
	.then(function(){
		var params = {
				  Bucket: testBucketName /* required */
		};
		return getBucketWebsite(params, s3);
	})
	.then(function(){
		var params = {
				  Bucket: testBucketName /* required */
		};
		return deleteBucketWebsite (params, s3)
	});

		
	// var params = {
	//   Bucket: testBucketName, /* required */
	//   WebsiteConfiguration: { /* required */
	//     ErrorDocument: {
	//       Key: 'error.html' /* required */
	//     },
	//     IndexDocument: {
	//       Suffix: 'index.html' /* required */
	//     },
	    
	//   },
	//   // ContentMD5: 'STRING_VALUE'
	// };
	// s3.putBucketWebsite(params, function(err, data) {
	//   if (err) console.log(err, err.stack); // an error occurred
	//   else     {
	//   	console.log(data);
	//   	getAndDeleteBucketWebsite(testBucketName);
	//   }           // successful response
	// });
}




function putBucketWebsite(params, s3)
{
	var deferred = Q.defer();
	s3.putBucketWebsite(params, function(err, data) {
		  if (err) console.log(err, err.stack); // an error occurred
		  else     {
		  	console.log(data);
			deferred.resolve();
		  }           // successful response
	});
	return deferred.promise;
}

function getBucketWebsite(params, s3)
{
	var deferred = Q.defer();

	s3.getBucketWebsite(params, function(err, data) {
		  if (err) console.log(err, err.stack); // an error occurred
		  else     {
		  	console.log(data);
			deferred.resolve();
		  }           // successful response
	});
	return deferred.promise;
}


function deleteBucketWebsite (params, s3){
	var deferred = Q.defer();

	s3.deleteBucketWebsite(params, function(err, data) {
	  if (err) console.log(err, err.stack); // an error occurred
	  else     {
		  	console.log(data);
			deferred.resolve();
	  }
	});
	return deferred.promise;
}


// function getAndDeleteBucketWebsite (bucketName){
// 	/* body... */
// 	var params = {
// 	  Bucket: bucketName /* required */
// 	};
// 	s3.getBucketWebsite(params, function(err, data) {
// 	  if (err) console.log(err, err.stack); // an error occurred
// 	  else     {
// 	  	console.log(data);
// 	  	deleteBucketWebsite (bucketName);
// 	  }           // successful response
// 	});
// }




// function deleteBucketWebsite (bucketName){
// 	var params = {
// 	  Bucket: bucketName /* required */
// 	};
// 	s3.deleteBucketWebsite(params, function(err, data) {
// 	  if (err) console.log(err, err.stack); // an error occurred
// 	  else     console.log(data);           // successful response
// 	});
// }
