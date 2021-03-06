/*
 * Copyright 2010-2013 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 * 
 *  http://aws.amazon.com/apache2.0
 * 
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
using System;
using System.Collections.Generic;
using System.Xml.Serialization;
using System.Text;
using System.IO;

namespace Amazon.Route53.Model
{
    /// <summary>
    /// <para>A complex type containing information about the specified health check.</para>
    /// </summary>
    public class GetHealthCheckResult
    {
        
        private HealthCheck healthCheck;

        /// <summary>
        /// A complex type that contains the information about the specified health check.
        ///  
        /// </summary>
        public HealthCheck HealthCheck
        {
            get { return this.healthCheck; }
            set { this.healthCheck = value; }
        }

        /// <summary>
        /// Sets the HealthCheck property
        /// </summary>
        /// <param name="healthCheck">The value to set for the HealthCheck property </param>
        /// <returns>this instance</returns>
        public GetHealthCheckResult WithHealthCheck(HealthCheck healthCheck)
        {
            this.healthCheck = healthCheck;
            return this;
        }
            

        // Check to see if HealthCheck property is set
        internal bool IsSetHealthCheck()
        {
            return this.healthCheck != null;
        }
    }
}
