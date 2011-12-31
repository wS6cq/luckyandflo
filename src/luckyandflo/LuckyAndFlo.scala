package luckyandflo

import org.apache.commons.io.FileUtils
import java.io.File
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

object LuckyAndFlo {

  def main(args: Array[String]) {

    val project = new Project(Settings.readArgs(args))
    project.title = "A first lucky and flo project"

    if (project.settings.complete) {

      project.load()
      println(project.settings)
      print(project.rules.toString())
      printf(project.root.toString())

      project.saveToFile()

      println("files: " + FileNode.fileCount + ", directories: " + FileNode.dirCount)
      println("violations: " + project.violations)

    } else {
        println("Sorry, wrong arguments provided!")
        println(printInfo())
    }
  }


  def printInfo():String = {
     "The following arguments are expected:\n" +
         "\t --rules <filepath> (path to xml ruleset)\n" +
         "\t --directory <directory> (directory that will be analysed)\n" +
         "\t --output <directory> (directory for output)\n"
  }

}