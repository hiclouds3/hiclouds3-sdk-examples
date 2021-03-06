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


namespace Amazon.Runtime
{
    /// <summary>
    /// Base class for request used by some of the services.
    /// </summary>
    public abstract partial class AmazonWebServiceRequest
    {
        internal event RequestEventHandler BeforeRequestEvent;
        internal EventHandler<StreamTransferProgressArgs> StreamUploadProgressCallback;

        internal AmazonWebServiceRequest WithBeforeRequestHandler(RequestEventHandler handler)
        {
            BeforeRequestEvent += handler;
            return this;
        }

        internal void FireBeforeRequestEvent(object sender, RequestEventArgs args)
        {
            if (BeforeRequestEvent != null)
                BeforeRequestEvent(sender, args);
        }
    }
}
