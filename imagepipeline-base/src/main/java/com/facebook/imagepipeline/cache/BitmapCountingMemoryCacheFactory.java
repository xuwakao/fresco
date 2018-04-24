/*
 * Copyright (c) 2015-present, Facebook, Inc.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.facebook.imagepipeline.cache;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.Supplier;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.image.CloseableImage;

public class BitmapCountingMemoryCacheFactory {

  public static CountingMemoryCache<CacheKey, CloseableImage> get(
          Supplier<MemoryCacheParams> bitmapMemoryCacheParamsSupplier,
          MemoryTrimmableRegistry memoryTrimmableRegistry,
          PlatformBitmapFactory platformBitmapFactory,
          boolean isExternalCreatedBitmapLogEnabled) {
    return get(
            bitmapMemoryCacheParamsSupplier,
            memoryTrimmableRegistry,
            platformBitmapFactory,
            isExternalCreatedBitmapLogEnabled,
            new BitmapMemoryCacheTrimStrategy());
  }

  public static CountingMemoryCache<CacheKey, CloseableImage> get(
     Supplier<MemoryCacheParams> bitmapMemoryCacheParamsSupplier,
     MemoryTrimmableRegistry memoryTrimmableRegistry,
     PlatformBitmapFactory platformBitmapFactory,
     boolean isExternalCreatedBitmapLogEnabled,
     CountingMemoryCache.CacheTrimStrategy trimStrategy) {

    ValueDescriptor<CloseableImage> valueDescriptor =
        new ValueDescriptor<CloseableImage>() {
          @Override
          public int getSizeInBytes(CloseableImage value) {
            return value.getSizeInBytes();
          }
        };

    CountingMemoryCache<CacheKey, CloseableImage> countingCache =
        new CountingMemoryCache<>(
            valueDescriptor,
            trimStrategy,
            bitmapMemoryCacheParamsSupplier,
            platformBitmapFactory,
            isExternalCreatedBitmapLogEnabled);

     memoryTrimmableRegistry.registerMemoryTrimmable(countingCache);

    return countingCache;
  }
}
