/*
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.facebook.drawee.debug.listener;

import android.graphics.drawable.Animatable;
import com.facebook.drawee.controller.BaseControllerListener;
import javax.annotation.Nullable;

/**
 * Currently we are measuring this from Submit to Final Image.But can be extended to include
 * intermediate time and failure cases also
 */
public class ImageLoadingTimeControllerListener extends BaseControllerListener {

  private long mRequestSubmitTimeMs = -1l;

  private long mFinalImageSetTimeMs = -1l;

  private @Nullable ImageLoadingTimeListener mImageLoadingTimeListener;

  public ImageLoadingTimeControllerListener(
      @Nullable ImageLoadingTimeListener imageLoadingTimeListener) {
    mImageLoadingTimeListener = imageLoadingTimeListener;
  }

  @Override
  public void onSubmit(String id, Object callerContext) {
    mRequestSubmitTimeMs = System.currentTimeMillis();
  }

  @Override
  public void onFinalImageSet(
      String id, @Nullable Object imageInfo, @Nullable Animatable animatable) {
    mFinalImageSetTimeMs = System.currentTimeMillis();
    if (mImageLoadingTimeListener != null) {
      mImageLoadingTimeListener.onFinalImageSet(mFinalImageSetTimeMs - mRequestSubmitTimeMs);
    }
  }
}
