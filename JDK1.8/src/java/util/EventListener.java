/*
 * Copyright (c) 1996, 1999, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package java.util;

/**
 * A tagging interface that all event listener interfaces must extend.
 * @since JDK1.1
 * @date 20200412
 * 描述事件监听器
 * Java中的事件监听机制主要由事件源、事件对象、事件监听器三个部分组成
 * 在Java中，通过java.util. EventObject来描述事件，通过java.util. EventListener来描述事件监听器，
 * 在众多的框架和组件中，建立一套事件机制通常是基于这两个接口来进行扩展
 */
public interface EventListener {
}
