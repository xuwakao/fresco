/*
 * Copyright (c) 2017-present, Facebook, Inc.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.facebook.imagepipeline.request;

import javax.annotation.Nullable;

public interface HasImageRequest {

  @Nullable
  ImageRequest getImageRequest();
}
