package luckyandflo

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


abstract class Violation {
	
	var description:String = "no description"
  var message:String = "no message"
  var line = 0
  var col = 0
  var severity = "low"
  var file = ""
  var snippet = ""


  def toString(indent:String) =
    "Line: " + line + ", " + message + ", " + description +  " " + " (" + file + ")"

  override def toString() = toString("")

  def toCheckStyleXml() = {
    <violation
      message={message}
      description={description}
      line={line.toString}
      col={col.toString}
      severity={severity} />
  }

  def toHtmlList() = {
    <tr class="htmlList">
      <td>{line.toString}</td>
      <td>{col.toString}</td>
      <td>{message}</td>
      <td>{description}</td>
      <td>{severity}</td>
    </tr>
    <tr class="htmlList">
        <td colspan="6" class="code violation">{ if (snippet.length > 255) snippet.substring(1, 255) else snippet }</td>
    </tr>
  }

}
