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

namespace Amazon.DirectConnect.Model
{
    /// <summary>
    /// 
    /// </summary>
    public class NewPublicVirtualInterface  
    {
        
        private string virtualInterfaceName;
        private int? vlan;
        private int? asn;
        private string authKey;
        private string amazonAddress;
        private string customerAddress;
        private List<RouteFilterPrefix> routeFilterPrefixes = new List<RouteFilterPrefix>();

        /// <summary>
        /// The name of the virtual interface assigned by the customer Example: "Dev VPC"
        ///  
        /// </summary>
        public string VirtualInterfaceName
        {
            get { return this.virtualInterfaceName; }
            set { this.virtualInterfaceName = value; }
        }

        /// <summary>
        /// Sets the VirtualInterfaceName property
        /// </summary>
        /// <param name="virtualInterfaceName">The value to set for the VirtualInterfaceName property </param>
        /// <returns>this instance</returns>
        public NewPublicVirtualInterface WithVirtualInterfaceName(string virtualInterfaceName)
        {
            this.virtualInterfaceName = virtualInterfaceName;
            return this;
        }
            

        // Check to see if VirtualInterfaceName property is set
        internal bool IsSetVirtualInterfaceName()
        {
            return this.virtualInterfaceName != null;       
        }

        /// <summary>
        /// VLAN ID Example: 101
        ///  
        /// </summary>
        public int Vlan
        {
            get { return this.vlan ?? default(int); }
            set { this.vlan = value; }
        }

        /// <summary>
        /// Sets the Vlan property
        /// </summary>
        /// <param name="vlan">The value to set for the Vlan property </param>
        /// <returns>this instance</returns>
        public NewPublicVirtualInterface WithVlan(int vlan)
        {
            this.vlan = vlan;
            return this;
        }
            

        // Check to see if Vlan property is set
        internal bool IsSetVlan()
        {
            return this.vlan.HasValue;       
        }

        /// <summary>
        /// AS number for BGP configuration Example: 65000
        ///  
        /// </summary>
        public int Asn
        {
            get { return this.asn ?? default(int); }
            set { this.asn = value; }
        }

        /// <summary>
        /// Sets the Asn property
        /// </summary>
        /// <param name="asn">The value to set for the Asn property </param>
        /// <returns>this instance</returns>
        public NewPublicVirtualInterface WithAsn(int asn)
        {
            this.asn = asn;
            return this;
        }
            

        // Check to see if Asn property is set
        internal bool IsSetAsn()
        {
            return this.asn.HasValue;       
        }

        /// <summary>
        /// Authentication key for BGP configuration Example: asdf345vjkl12
        ///  
        /// </summary>
        public string AuthKey
        {
            get { return this.authKey; }
            set { this.authKey = value; }
        }

        /// <summary>
        /// Sets the AuthKey property
        /// </summary>
        /// <param name="authKey">The value to set for the AuthKey property </param>
        /// <returns>this instance</returns>
        public NewPublicVirtualInterface WithAuthKey(string authKey)
        {
            this.authKey = authKey;
            return this;
        }
            

        // Check to see if AuthKey property is set
        internal bool IsSetAuthKey()
        {
            return this.authKey != null;       
        }

        /// <summary>
        /// Address assigned to the Amazon interface. Example: 192.168.1.1
        ///  
        /// </summary>
        public string AmazonAddress
        {
            get { return this.amazonAddress; }
            set { this.amazonAddress = value; }
        }

        /// <summary>
        /// Sets the AmazonAddress property
        /// </summary>
        /// <param name="amazonAddress">The value to set for the AmazonAddress property </param>
        /// <returns>this instance</returns>
        public NewPublicVirtualInterface WithAmazonAddress(string amazonAddress)
        {
            this.amazonAddress = amazonAddress;
            return this;
        }
            

        // Check to see if AmazonAddress property is set
        internal bool IsSetAmazonAddress()
        {
            return this.amazonAddress != null;       
        }
        public string CustomerAddress
        {
            get { return this.customerAddress; }
            set { this.customerAddress = value; }
        }

        /// <summary>
        /// Sets the CustomerAddress property
        /// </summary>
        /// <param name="customerAddress">The value to set for the CustomerAddress property </param>
        /// <returns>this instance</returns>
        public NewPublicVirtualInterface WithCustomerAddress(string customerAddress)
        {
            this.customerAddress = customerAddress;
            return this;
        }
            

        // Check to see if CustomerAddress property is set
        internal bool IsSetCustomerAddress()
        {
            return this.customerAddress != null;       
        }

        /// <summary>
        /// A list of route filter prefixes.
        ///  
        /// </summary>
        public List<RouteFilterPrefix> RouteFilterPrefixes
        {
            get { return this.routeFilterPrefixes; }
            set { this.routeFilterPrefixes = value; }
        }
        /// <summary>
        /// Adds elements to the RouteFilterPrefixes collection
        /// </summary>
        /// <param name="routeFilterPrefixes">The values to add to the RouteFilterPrefixes collection </param>
        /// <returns>this instance</returns>
        public NewPublicVirtualInterface WithRouteFilterPrefixes(params RouteFilterPrefix[] routeFilterPrefixes)
        {
            foreach (RouteFilterPrefix element in routeFilterPrefixes)
            {
                this.routeFilterPrefixes.Add(element);
            }

            return this;
        }
        
        /// <summary>
        /// Adds elements to the RouteFilterPrefixes collection
        /// </summary>
        /// <param name="routeFilterPrefixes">The values to add to the RouteFilterPrefixes collection </param>
        /// <returns>this instance</returns>
        public NewPublicVirtualInterface WithRouteFilterPrefixes(IEnumerable<RouteFilterPrefix> routeFilterPrefixes)
        {
            foreach (RouteFilterPrefix element in routeFilterPrefixes)
            {
                this.routeFilterPrefixes.Add(element);
            }

            return this;
        }

        // Check to see if RouteFilterPrefixes property is set
        internal bool IsSetRouteFilterPrefixes()
        {
            return this.routeFilterPrefixes.Count > 0;       
        }
    }
}
