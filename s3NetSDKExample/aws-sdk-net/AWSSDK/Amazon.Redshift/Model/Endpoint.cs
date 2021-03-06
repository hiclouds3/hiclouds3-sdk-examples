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

namespace Amazon.Redshift.Model
{
    /// <summary>
    /// <para>Describes a connection endpoint.</para>
    /// </summary>
    public class Endpoint
    {
        
        private string address;
        private int? port;

        /// <summary>
        /// The DNS address of the Cluster.
        ///  
        /// </summary>
        public string Address
        {
            get { return this.address; }
            set { this.address = value; }
        }

        /// <summary>
        /// Sets the Address property
        /// </summary>
        /// <param name="address">The value to set for the Address property </param>
        /// <returns>this instance</returns>
        public Endpoint WithAddress(string address)
        {
            this.address = address;
            return this;
        }
            

        // Check to see if Address property is set
        internal bool IsSetAddress()
        {
            return this.address != null;
        }

        /// <summary>
        /// The port that the database engine is listening on.
        ///  
        /// </summary>
        public int Port
        {
            get { return this.port ?? default(int); }
            set { this.port = value; }
        }

        /// <summary>
        /// Sets the Port property
        /// </summary>
        /// <param name="port">The value to set for the Port property </param>
        /// <returns>this instance</returns>
        public Endpoint WithPort(int port)
        {
            this.port = port;
            return this;
        }
            

        // Check to see if Port property is set
        internal bool IsSetPort()
        {
            return this.port.HasValue;
        }
    }
}
