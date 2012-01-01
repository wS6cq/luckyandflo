package luckyandflo

import xml.NodeBuffer

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
 * Convert objects into a markup serialization
 */
object MarkupPartials {

  /**
   * Serialize Violation to XML
   */
  def toCheckStyleXml(violation:Violation) = {
      <violation
      message={violation.message}
      description={violation.description}
      line={violation.line.toString}
      col={violation.col.toString}
      severity={violation.severity} />
  }

  /**
   * Serialize Violation to HTML
   */
  def toHtmlList(violation:Violation):NodeBuffer  = {
    <tr class="htmlList">
      <td>{violation.line.toString}</td>
      <td>{violation.col.toString}</td>
      <td>{violation.message}</td>
      <td>{violation.description}</td>
      <td>{violation.severity}</td>
    </tr>
      <tr class="htmlList">
        <td colspan="6" class="code violation">{ if (violation.snippet.length > 255) violation.snippet.substring(1, 255) else violation.snippet }</td>
      </tr>
  }

  /**
   * Serialize FileNode to XML
   */
  def toCheckStyleXml(fileNode:FileNode):NodeBuffer = {
    val listBuffer = new NodeBuffer
    for (node <- fileNode.nodes) {listBuffer &+ (<file path={node.path}>{MarkupPartials.toCheckStyleXml(node.violations)}</file>) &+ MarkupPartials.toCheckStyleXml(node) }
    listBuffer
  }

  /**
   * Serialize FileNode HTML
   */
  def toHtmlList(fileNode:FileNode):NodeBuffer = {
    val listBuffer = new NodeBuffer
    for (node <- fileNode.nodes) {
      if (node.violations.items.length > 0)  {
        listBuffer &+
          (<p><a href={"file:///" + node.path}><h3>File: {node.path}</h3></a><table><tr><th>Line</th><th>Col</th><th>Message</th><th>Description</th><th>Severity</th></tr>{MarkupPartials.toHtml(node.violations)}</table></p>)
      }
      listBuffer &+ MarkupPartials.toHtmlList(node) // recursion
    }
    listBuffer
  }

  /**
   * Serialize ViolationList to XML
   */
  def toCheckStyleXml(violationList:ViolationList):NodeBuffer  = {
    val listBuffer = new NodeBuffer
    for (item <- violationList.items) { listBuffer &+ MarkupPartials.toCheckStyleXml(item) }
    listBuffer
  }

  /**
   * Serialize ViolationList to HTML
   */
  def toHtml(violationList:ViolationList):NodeBuffer  = {
    val listBuffer = new NodeBuffer
    for (item <-  violationList.items) { listBuffer &+ MarkupPartials.toHtmlList(item) }
    listBuffer
  }

}


class MarkupPartials {



}