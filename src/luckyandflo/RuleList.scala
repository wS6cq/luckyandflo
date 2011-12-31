package luckyandflo

import collection.mutable.ListBuffer
import org.apache.commons.io.FileUtils
import java.io.File

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

object RuleList {

  /**
   * Read a list of rules from an xml file
   */
  def loadFromXml(filePath:String):RuleList = {
    var ruleList = new RuleList
    val xmlContent = xml.XML.loadFile(filePath)
    xmlContent match {
      case <rulelist>{therms @ _*}</rulelist> =>
        for (therm <- therms) {
          if (!therm.isAtom) ruleList.items += Rule.fromXmlNode(therm)
        }
    }
    ruleList
  }
}

class RuleList {

  var items = new ListBuffer[Rule]

  /**
   * A list of rules is here applied to a file node. This returns
   * a list of violations against the rules.
   */
  def applyToFile(node:FileNode, recursive:Boolean):Int = {
    var count = 0

    if (node.file) {
      for (rule <- items) { // every rule
        if ( rule.enabled && node.path.matches(rule.include) && !node.path.matches(rule.exclude) ) {
          var iterator = FileUtils.lineIterator( new File(node.path))
          var line = 1
          while (iterator.hasNext) { // every line
            var currentLine = iterator.next()
              if ( !(currentLine equals "") && (currentLine.matches(rule.content)) ) {
                   val v = new LineViolation
                   v.message = rule.message.replace("{$value}", rule.content)
                   v.description = rule.description
                   v.line = line
                   v.file = node.path
                   v.snippet = currentLine
                   node.violations.items += v
                   count += 1
              }
            line += 1
          }
        }
      }
    }

    if (recursive && node.directory) {
      for (node <- node.nodes) count += applyToFile(node, recursive)
    }
    count
  }

  /**
   * Serialize to string
   */
  override def toString() = {
    var i = 0
    var result = "Rule list (" + items.length + ")\n"
    for (item <- items) { i+=1; result += i + ": " + item + "\n"}
    result
  }


}