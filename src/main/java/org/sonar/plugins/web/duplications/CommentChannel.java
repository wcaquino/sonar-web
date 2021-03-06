/*
 * SonarQube Web Plugin
 * Copyright (C) 2010 SonarSource and Matthijs Galesloot
 * sonarqube@googlegroups.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sonar.plugins.web.duplications;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pmd.cpd.Tokens;

import org.sonar.channel.Channel;
import org.sonar.channel.CodeReader;

public class CommentChannel extends Channel<Tokens> {

  private final Matcher matcher;
  private final EmptyAppendable appendable = new EmptyAppendable();

  public static final CommentChannel HTML_COMMENT = new CommentChannel("<!--[\\w\\W]*?-->");
  public static final CommentChannel JSP_COMMENT = new CommentChannel("<%--[\\w\\W]*?%>");
  public static final CommentChannel CPP_COMMENT = new CommentChannel("//[^\n\r]*");
  public static final CommentChannel C_COMMENT = new CommentChannel("/\\*[\\w\\W]*?\\*/");

  public CommentChannel(String regex) {
    this.matcher = Pattern.compile(regex).matcher("");
  }

  @Override
  public boolean consume(CodeReader code, Tokens cpdTokens) {
    return code.popTo(matcher, appendable) > 0;
  }

  private static class EmptyAppendable implements Appendable {

    @Override
    public Appendable append(CharSequence csq) throws IOException {
      return null;
    }

    @Override
    public Appendable append(char c) throws IOException {
      return null;
    }

    @Override
    public Appendable append(CharSequence csq, int start, int end) throws IOException {
      return null;
    }
  }
}
