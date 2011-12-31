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

object MarkupTemplates{

  def toHtmlList(project:Project) = {
    <html>
      <head>
        <title>{project.title}</title>
        <style type="text/css">
         <!--
          .code{
            font-family: "Courier New", Courier, monospace;
            font-size: 12px;
          }
          -->
        </style>
      </head>
      <body>
        <h1>{project.title}</h1>
        <p>Directory: {project.root.path}</p>
        <p>Rules: {project.settings.rules}</p>
        <p>Output: {project.settings.output}</p>
        <h2>Rules</h2>
        <pre>{project.rules.toString}</pre>
        <h2>Violations</h2>
        {project.root.toHtmlList()}
        <h2>Violations (Text)</h2>
        <pre>{project.root.toString()}</pre>
      </body>
    </html>
  }

  def toCheckStyleXml(project:Project) = {
    <root directory={project.root.path} projecttitle={project.title}>{project.root.toCheckStyleXml()}</root>
  }
}

class MarkupTemplates {

}