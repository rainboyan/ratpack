/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ratpackframework.registry.internal;

import com.google.common.collect.ImmutableList;
import org.ratpackframework.registry.Registry;

import java.util.List;

public abstract class ChildRegistrySupport<T> extends RegistrySupport<T> {

  private final Registry<T> parent;

  protected ChildRegistrySupport(Registry<T> parent) {
    this.parent = parent;
  }

  @Override
  public String toString() {
    return describe() + " -> " + parent.toString();
  }

  @Override
  protected <O extends T> O onNotFound(@SuppressWarnings("UnusedParameters") Class<O> type) {
    return parent.maybeGet(type);
  }

  @Override
  protected final <O extends T> List<O> doGetAll(Class<O> type) {
    return ImmutableList.<O>builder().addAll(doChildGetAll(type)).addAll(parent.getAll(type)).build();
  }

  protected abstract <O extends T> List<O> doChildGetAll(Class<O> type);

  protected abstract String describe();

}
