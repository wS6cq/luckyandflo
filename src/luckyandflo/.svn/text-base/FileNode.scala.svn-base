package luckyandflo

import collection.mutable.ListBuffer
import java.io.{FileFilter, File}
import org.apache.commons.io.filefilter.FileFilterUtils
import xml.{NodeBuffer, Elem, NodeSeq, Node}

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

/**
 *
 */
object FileNode {

  var fileCount = 0
  var dirCount = 0

  /**
   * Crates an instance from a directory
   */
  def fromPath(path:String, extensions:Array[String], maxDepth:Int, maxFiles:Int, recursive:Boolean):FileNode = {
    val instance = new FileNode(path, 0)
    instance.extensions = extensions
    instance.maxDepth = maxDepth
    instance.maxFiles = maxFiles
    instance.loadFromDirectory(recursive)
    instance.directory = true
    instance
  }
}

/**
 * Class represents a file/directory
 */
class FileNode(var path:String, var level:Int) {

  var maxDepth = 8
  var maxFiles = 9999
  var extensions = Array[String]("php", "html", "css", "js", "java", "scala")
  var nodes = new ListBuffer[FileNode]
  var violations = new ViolationList
  var directory = false
  var file = false

  /**
   * Load file node from a directory
   */
  def loadFromDirectory(recursive:Boolean) {
    if (level==0) {
      FileNode.fileCount = 0
      FileNode.dirCount = 0
    }
    val file = new File(path)

    if (file.isDirectory && FileNode.fileCount < maxFiles ){

      nodes.clear()

      // if we are dealing with a directory
      for (item <- file.listFiles(FileFilterUtils.directoryFileFilter().asInstanceOf[FileFilter])){
        nodes += new FileNode(item.toString, level+1)
        nodes.last.directory = true
        FileNode.dirCount += 1
        // recursively iterate deeper into directory tree
        if (level<maxDepth) nodes.last.loadFromDirectory(true)
      }


      // if we are dealing with a file
      for (item <- file.listFiles(FileFilterUtils.fileFileFilter().asInstanceOf[FileFilter])){
          var target = false
          for (extension <- extensions) target |= item.toString.endsWith(extension)
          if (target) {
            nodes += new FileNode(item.toString, level+1)
            nodes.last.file = true
            FileNode.fileCount += 1
          }
        }
    }
  }

  /**
   * Serialize to XML
   */
  def toCheckStyleXml():NodeBuffer = {
    val listBuffer = new NodeBuffer
    for (node <- nodes) {listBuffer &+ (<file path={node.path}>{node.violations.toCheckStyleXml()}</file>) &+ node.toCheckStyleXml() }
    listBuffer
  }

  /**
   * Serialize to HTML
   */
  def toHtmlList():NodeBuffer = {
    val listBuffer = new NodeBuffer
    for (node <- nodes) {
      if (node.violations.items.length > 0)  {
        listBuffer &+
          (<p><a href={"file:///" + node.path}><h3>File: {node.path}</h3></a><table><tr><th>Line</th><th>Col</th><th>Message</th><th>Description</th><th>Severity</th></tr>{node.violations.toHtml()}</table></p>)
      }
      listBuffer &+ node.toHtmlList() // recursion
    }
    listBuffer
  }

  /**
   * Serialize to string
   */
  override def toString() = {
    var result = ""
    var indent = ""
    for (i <- 0 until level) indent += " |"
    result += indent
    result += path.split('\\').last
    if (directory) result += "/\n"
    if (file) result += "\n"
    result += violations.toString(indent + "--")
    for (node <- nodes) { result += node }
    result
  }

}