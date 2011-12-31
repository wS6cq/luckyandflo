package luckyandflo

import util.matching.Regex
import io.Source
import java.nio.charset.MalformedInputException

/**
 * Copyright (c) 2011, Phillip Kroll <contact@phillipkroll.de>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 * 
 */

object Rule {
  def fromXmlNode(node: scala.xml.Node):Rule = {
    val instance = new Rule
    instance.description = (node \ "description").text
    instance.message = (node \ "message").text
    instance.group = (node \ "group").text
    instance.include = (node \ "include").text
    instance.exclude = (node \ "exclude").text
    instance.content = (node \ "content").text
    instance.enabled = (node \ "enabled").text.toLowerCase() == "true"
    instance
  }
}

class Rule {

  var description = ""
  var message = ""
  var group = ""
  var include = ""
  var exclude = ""
  var content = ""
  var enabled = false



  override def toString() =
    "Rule: '" + description + "'\n" +
    "\tMessage: '" + message + "'\n" +
    "\tGroup: '" + group + "'\n" +
    "\tInclude: '" + include + "'\n" +
    "\tExclude: '" + exclude + "'\n" +
    "\tContent: '" + content + "'\n"
}