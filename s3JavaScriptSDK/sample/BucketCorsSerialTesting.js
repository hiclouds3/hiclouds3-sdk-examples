
//1. get bucketCors
//2. put bucketCors

function bucketCorsTesting (){
	var params = {
	  Bucket: testBucketName /* required */
	};

	s3.getBucketCors(params, function(err, data) {
	  if (err) console.log(err, err.stack); // an error occurred
	  else     {
	  	console.log(data);
	  	console.log('get bucketcors succeed');
		var params = {
		Bucket: testBucketName, /* required */
		CORSConfiguration: {
		CORSRules: [
		  {
		    AllowedHeaders: [
		      '*',
		    ],
		    AllowedMethods: [
		      'GET','PUT','DELETE','POST'
		    ],
		    AllowedOrigins: [
		      '*','null'
		    ],
		    ExposeHeaders: [
		      'ETag',
		    ],
		   },
		]
		},
		// ContentMD5: 'STRING_VALUE'
		};
		s3.putBucketCors(params, function(err, data) {
		if (err) console.log(err, err.stack); // an error occurred
		else     {
			console.log(data);
			console.log('put bucketcors succeed');
		}
		});
	  }          
	});
}







// var params = {
//   Bucket: 'testrubybucket1' /* required */
// };
// s3.deleteBucketCors(params, function(err, data) {
//   if (err) console.log(err, err.stack); // an error occurred
//   else     {
//   	console.log('delete bucketCors succeed');
//   	console.log(data);
//   }
//              // successful response
// });
