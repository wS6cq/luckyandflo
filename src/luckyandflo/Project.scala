package luckyandflo

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

class Project(initSettings:Settings) {

  var title = "New Project"
  val settings:Settings = initSettings

  var rules:RuleList = null
  var root:FileNode = null
  var violations:Int = 0

  def load() {
    if (settings.complete){
      rules = RuleList.loadFromXml(settings.rules)
      root = FileNode.fromPath(settings.directory, settings.extensions, settings.maxDepth, settings.maxFiles, true)
      violations = rules.applyToFile(root, true)
    }
  }

  def saveToFile() {
    scala.xml.XML.save( settings.output + "/output.xml", MarkupTemplates.toCheckStyleXml(this) )
    scala.xml.XML.save( settings.output + "/output.html", MarkupTemplates.toHtmlList(this))
    FileUtils.writeStringToFile(new File(settings.output + "/output.txt"), root.toString())
  }


}