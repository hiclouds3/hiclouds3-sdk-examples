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
using System.Text;

namespace Amazon.Auth.AccessControlPolicy.ActionIdentifiers
{
    /// <summary>
    /// The available AWS access control policy actions for Amazon SES.
    /// </summary>
    /// <see cref="Amazon.Auth.AccessControlPolicy.Statement.Actions"/>
    public class SESActionIdentifiers
    {
        public static readonly ActionIdentifier AllSESActions = new ActionIdentifier("ses:*");

        public static readonly ActionIdentifier DeleteIdentity = new ActionIdentifier("ses:DeleteIdentity");
        public static readonly ActionIdentifier DeleteVerifiedEmailAddress = new ActionIdentifier("ses:DeleteVerifiedEmailAddress");
        public static readonly ActionIdentifier GetIdentityVerificationAttributes = new ActionIdentifier("ses:GetIdentityVerificationAttributes");
        public static readonly ActionIdentifier GetSendQuota = new ActionIdentifier("ses:GetSendQuota");
        public static readonly ActionIdentifier GetSendStatistics = new ActionIdentifier("ses:GetSendStatistics");
        public static readonly ActionIdentifier ListIdentities = new ActionIdentifier("ses:ListIdentities");
        public static readonly ActionIdentifier ListVerifiedEmailAddresses = new ActionIdentifier("ses:ListVerifiedEmailAddresses");
        public static readonly ActionIdentifier SendEmail = new ActionIdentifier("ses:SendEmail");
        public static readonly ActionIdentifier SendRawEmail = new ActionIdentifier("ses:SendRawEmail");
        public static readonly ActionIdentifier VerifyDomainIdentity = new ActionIdentifier("ses:VerifyDomainIdentity");
        public static readonly ActionIdentifier VerifyEmailAddress = new ActionIdentifier("ses:VerifyEmailAddress");
        public static readonly ActionIdentifier VerifyEmailIdentity = new ActionIdentifier("ses:VerifyEmailIdentity");
    }
}
