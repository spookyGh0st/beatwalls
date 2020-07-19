(window.webpackJsonp=window.webpackJsonp||[]).push([[255],{578:function(t,e,r){"use strict";r.r(e);var o=r(26),s=Object(o.a)({},(function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("ContentSlotsDistributor",{attrs:{"slot-key":t.$parent.slotKey}},[r("p",[r("RouterLink",{attrs:{to:"/"}},[t._v("beatwalls")]),t._v(" / "),r("RouterLink",{attrs:{to:"/reference/"}},[t._v("structure")]),t._v(" / "),r("RouterLink",{attrs:{to:"/reference/-helix-curve/"}},[t._v("HelixCurve")])],1),t._v(" "),r("h1",{attrs:{id:"helixcurve"}},[r("a",{staticClass:"header-anchor",attrs:{href:"#helixcurve"}},[t._v("#")]),t._v(" HelixCurve")]),t._v(" "),r("p",[r("code",[t._v("class HelixCurve :")]),r("RouterLink",{attrs:{to:"/reference/-wall-structure/"}},[r("code",[t._v("WallStructure")])])],1),t._v(" "),r("p",[t._v("NOT IMPLEMENTED YET")]),t._v(" "),r("p",[t._v("Helix Curve lets you define 1/4 of a curve around the center. The program creates the rest of the helix")]),t._v(" "),r("h3",{attrs:{id:"constructors"}},[r("a",{staticClass:"header-anchor",attrs:{href:"#constructors"}},[t._v("#")]),t._v(" Constructors")]),t._v(" "),r("table",[r("thead",[r("tr",[r("th",[t._v("Name")]),t._v(" "),r("th",[t._v("Summary")])])]),t._v(" "),r("tbody",[r("tr",[r("td",[r("RouterLink",{attrs:{to:"/reference/-helix-curve/-init-.html"}},[t._v("<init>")])],1),t._v(" "),r("td",[t._v("NOT IMPLEMENTED YET"),r("code",[t._v("HelixCurve()")])])])])]),t._v(" "),r("h3",{attrs:{id:"properties"}},[r("a",{staticClass:"header-anchor",attrs:{href:"#properties"}},[t._v("#")]),t._v(" Properties")]),t._v(" "),r("table",[r("thead",[r("tr",[r("th",[t._v("Name")]),t._v(" "),r("th",[t._v("Summary")])])]),t._v(" "),r("tbody",[r("tr",[r("td",[r("RouterLink",{attrs:{to:"/reference/-helix-curve/amount.html"}},[t._v("amount")])],1),t._v(" "),r("td",[t._v("the amount of walls per Helix"),r("code",[t._v("var amount: Int")])])]),t._v(" "),r("tr",[r("td",[r("RouterLink",{attrs:{to:"/reference/-helix-curve/count.html"}},[t._v("count")])],1),t._v(" "),r("td",[t._v("how many helix spines will be Created. Only 2 or 4 allowed"),r("code",[t._v("var count: Int")])])]),t._v(" "),r("tr",[r("td",[r("RouterLink",{attrs:{to:"/reference/-helix-curve/p1.html"}},[t._v("p1")])],1),t._v(" "),r("td",[t._v("the start Point of the Curve"),r("code",[t._v("var p1:")]),r("RouterLink",{attrs:{to:"/structure.helper-classes/-point/"}},[r("code",[t._v("Point")])])],1)]),t._v(" "),r("tr",[r("td",[r("RouterLink",{attrs:{to:"/reference/-helix-curve/p2.html"}},[t._v("p2")])],1),t._v(" "),r("td",[t._v("the first Controllpoint, defaults to the startPoint"),r("code",[t._v("var p2:")]),r("RouterLink",{attrs:{to:"/structure.helper-classes/-point/"}},[r("code",[t._v("Point")])])],1)]),t._v(" "),r("tr",[r("td",[r("RouterLink",{attrs:{to:"/reference/-helix-curve/p3.html"}},[t._v("p3")])],1),t._v(" "),r("td",[t._v("second ControlPoint, defaults to the end point"),r("code",[t._v("var p3:")]),r("RouterLink",{attrs:{to:"/structure.helper-classes/-point/"}},[r("code",[t._v("Point")])])],1)]),t._v(" "),r("tr",[r("td",[r("RouterLink",{attrs:{to:"/reference/-helix-curve/p4.html"}},[t._v("p4")])],1),t._v(" "),r("td",[t._v("The EndPoint of the Curve"),r("code",[t._v("var p4:")]),r("RouterLink",{attrs:{to:"/structure.helper-classes/-point/"}},[r("code",[t._v("Point")])])],1)])])]),t._v(" "),r("h3",{attrs:{id:"functions"}},[r("a",{staticClass:"header-anchor",attrs:{href:"#functions"}},[t._v("#")]),t._v(" Functions")]),t._v(" "),r("table",[r("thead",[r("tr",[r("th",[t._v("Name")]),t._v(" "),r("th",[t._v("Summary")])])]),t._v(" "),r("tbody",[r("tr",[r("td",[r("RouterLink",{attrs:{to:"/reference/-helix-curve/generate-walls.html"}},[t._v("generateWalls")])],1),t._v(" "),r("td",[t._v("generating the Walls"),r("code",[t._v("fun generateWalls(): List<")]),r("RouterLink",{attrs:{to:"/structure.helper-classes/-spooky-wall/"}},[r("code",[t._v("SpookyWall")])]),r("code",[t._v(">")])],1)])])]),t._v(" "),r("h3",{attrs:{id:"extension-functions"}},[r("a",{staticClass:"header-anchor",attrs:{href:"#extension-functions"}},[t._v("#")]),t._v(" Extension Functions")]),t._v(" "),r("table",[r("thead",[r("tr",[r("th",[t._v("Name")]),t._v(" "),r("th",[t._v("Summary")])])]),t._v(" "),r("tbody",[r("tr",[r("td",[r("RouterLink",{attrs:{to:"/structure.wallbender/bend-walls.html"}},[t._v("bendWalls")])],1),t._v(" "),r("td",[r("code",[t._v("fun")]),r("RouterLink",{attrs:{to:"/reference/-wall-structure/"}},[r("code",[t._v("WallStructure")])]),r("code",[t._v(".bendWalls(walls: List<")]),r("RouterLink",{attrs:{to:"/structure.helper-classes/-spooky-wall/"}},[r("code",[t._v("SpookyWall")])]),r("code",[t._v(">): List<")]),r("RouterLink",{attrs:{to:"/structure.helper-classes/-spooky-wall/"}},[r("code",[t._v("SpookyWall")])]),r("code",[t._v(">")])],1)]),t._v(" "),r("tr",[r("td",[r("RouterLink",{attrs:{to:"/structure.wallbender/color.html"}},[t._v("color")])],1),t._v(" "),r("td",[r("code",[t._v("fun")]),r("RouterLink",{attrs:{to:"/reference/-wall-structure/"}},[r("code",[t._v("WallStructure")])]),r("code",[t._v(".color(l: List<")]),r("RouterLink",{attrs:{to:"/structure.helper-classes/-spooky-wall/"}},[r("code",[t._v("SpookyWall")])]),r("code",[t._v(">): List<")]),r("RouterLink",{attrs:{to:"/structure.helper-classes/-spooky-wall/"}},[r("code",[t._v("SpookyWall")])]),r("code",[t._v(">")])],1)]),t._v(" "),r("tr",[r("td",[r("RouterLink",{attrs:{to:"/reference/copy-walls.html"}},[t._v("copyWalls")])],1),t._v(" "),r("td",[r("code",[t._v("fun")]),r("RouterLink",{attrs:{to:"/reference/-wall-structure/"}},[r("code",[t._v("WallStructure")])]),r("code",[t._v(".copyWalls(): ArrayList<")]),r("RouterLink",{attrs:{to:"/structure.helper-classes/-spooky-wall/"}},[r("code",[t._v("SpookyWall")])]),r("code",[t._v(">")])],1)]),t._v(" "),r("tr",[r("td",[r("RouterLink",{attrs:{to:"/reference/deep-copy.html"}},[t._v("deepCopy")])],1),t._v(" "),r("td",[r("code",[t._v("fun")]),r("RouterLink",{attrs:{to:"/reference/-wall-structure/"}},[r("code",[t._v("WallStructure")])]),r("code",[t._v(".deepCopy():")]),r("RouterLink",{attrs:{to:"/reference/-wall-structure/"}},[r("code",[t._v("WallStructure")])])],1)]),t._v(" "),r("tr",[r("td",[r("RouterLink",{attrs:{to:"/structure.wallbender/generate-bend-and-repeat-walls.html"}},[t._v("generateBendAndRepeatWalls")])],1),t._v(" "),r("td",[r("code",[t._v("fun")]),r("RouterLink",{attrs:{to:"/reference/-wall-structure/"}},[r("code",[t._v("WallStructure")])]),r("code",[t._v(".generateBendAndRepeatWalls(): List<")]),r("RouterLink",{attrs:{to:"/structure.helper-classes/-spooky-wall/"}},[r("code",[t._v("SpookyWall")])]),r("code",[t._v(">")])],1)]),t._v(" "),r("tr",[r("td",[r("RouterLink",{attrs:{to:"/structure.wallbender/mirror.html"}},[t._v("mirror")])],1),t._v(" "),r("td",[r("code",[t._v("fun")]),r("RouterLink",{attrs:{to:"/reference/-wall-structure/"}},[r("code",[t._v("WallStructure")])]),r("code",[t._v(".mirror(l: List<")]),r("RouterLink",{attrs:{to:"/structure.helper-classes/-spooky-wall/"}},[r("code",[t._v("SpookyWall")])]),r("code",[t._v(">): List<")]),r("RouterLink",{attrs:{to:"/structure.helper-classes/-spooky-wall/"}},[r("code",[t._v("SpookyWall")])]),r("code",[t._v(">")])],1)]),t._v(" "),r("tr",[r("td",[r("RouterLink",{attrs:{to:"/structure.special-strucures/read-point.html"}},[t._v("readPoint")])],1),t._v(" "),r("td",[r("code",[t._v("fun")]),r("RouterLink",{attrs:{to:"/reference/-wall-structure/"}},[r("code",[t._v("WallStructure")])]),r("code",[t._v(".readPoint(name: String):")]),r("RouterLink",{attrs:{to:"/structure.helper-classes/-point/"}},[r("code",[t._v("Point")])]),r("code",[t._v("?")])],1)]),t._v(" "),r("tr",[r("td",[r("RouterLink",{attrs:{to:"/structure.wallbender/repeat.html"}},[t._v("repeat")])],1),t._v(" "),r("td",[r("code",[t._v("fun")]),r("RouterLink",{attrs:{to:"/reference/-wall-structure/"}},[r("code",[t._v("WallStructure")])]),r("code",[t._v(".repeat(walls: List<")]),r("RouterLink",{attrs:{to:"/structure.helper-classes/-spooky-wall/"}},[r("code",[t._v("SpookyWall")])]),r("code",[t._v(">): List<")]),r("RouterLink",{attrs:{to:"/structure.helper-classes/-spooky-wall/"}},[r("code",[t._v("SpookyWall")])]),r("code",[t._v(">")])],1)]),t._v(" "),r("tr",[r("td",[r("RouterLink",{attrs:{to:"/structure.wallbender/repeat-struct.html"}},[t._v("repeatStruct")])],1),t._v(" "),r("td",[r("code",[t._v("fun")]),r("RouterLink",{attrs:{to:"/reference/-wall-structure/"}},[r("code",[t._v("WallStructure")])]),r("code",[t._v(".repeatStruct(walls: List<")]),r("RouterLink",{attrs:{to:"/structure.helper-classes/-spooky-wall/"}},[r("code",[t._v("SpookyWall")])]),r("code",[t._v(">): List<")]),r("RouterLink",{attrs:{to:"/structure.helper-classes/-spooky-wall/"}},[r("code",[t._v("SpookyWall")])]),r("code",[t._v(">")])],1)]),t._v(" "),r("tr",[r("td",[r("RouterLink",{attrs:{to:"/structure.wallbender/repeat-walls.html"}},[t._v("repeatWalls")])],1),t._v(" "),r("td",[r("code",[t._v("fun")]),r("RouterLink",{attrs:{to:"/reference/-wall-structure/"}},[r("code",[t._v("WallStructure")])]),r("code",[t._v(".repeatWalls(walls: List<")]),r("RouterLink",{attrs:{to:"/structure.helper-classes/-spooky-wall/"}},[r("code",[t._v("SpookyWall")])]),r("code",[t._v(">): List<")]),r("RouterLink",{attrs:{to:"/structure.helper-classes/-spooky-wall/"}},[r("code",[t._v("SpookyWall")])]),r("code",[t._v(">")])],1)]),t._v(" "),r("tr",[r("td",[r("RouterLink",{attrs:{to:"/structure.helper-functions/reset.html"}},[t._v("reset")])],1),t._v(" "),r("td",[r("code",[t._v("fun")]),r("RouterLink",{attrs:{to:"/reference/-wall-structure/"}},[r("code",[t._v("WallStructure")])]),r("code",[t._v(".reset(): Unit")])],1)]),t._v(" "),r("tr",[r("td",[r("RouterLink",{attrs:{to:"/structure.wallbender/rotate.html"}},[t._v("rotate")])],1),t._v(" "),r("td",[r("code",[t._v("fun")]),r("RouterLink",{attrs:{to:"/reference/-wall-structure/"}},[r("code",[t._v("WallStructure")])]),r("code",[t._v(".rotate(l: List<")]),r("RouterLink",{attrs:{to:"/structure.helper-classes/-spooky-wall/"}},[r("code",[t._v("SpookyWall")])]),r("code",[t._v(">): List<")]),r("RouterLink",{attrs:{to:"/structure.helper-classes/-spooky-wall/"}},[r("code",[t._v("SpookyWall")])]),r("code",[t._v(">")])],1)]),t._v(" "),r("tr",[r("td",[r("RouterLink",{attrs:{to:"/structure.special-strucures/run.html"}},[t._v("run")])],1),t._v(" "),r("td",[r("code",[t._v("fun")]),r("RouterLink",{attrs:{to:"/reference/-helix-curve/"}},[r("code",[t._v("HelixCurve")])]),r("code",[t._v(".run(): List<")]),r("RouterLink",{attrs:{to:"/structure.helper-classes/-spooky-wall/"}},[r("code",[t._v("SpookyWall")])]),r("code",[t._v(">")])],1)]),t._v(" "),r("tr",[r("td",[r("RouterLink",{attrs:{to:"/structure.wallbender/speeder.html"}},[t._v("speeder")])],1),t._v(" "),r("td",[r("code",[t._v("fun")]),r("RouterLink",{attrs:{to:"/reference/-wall-structure/"}},[r("code",[t._v("WallStructure")])]),r("code",[t._v(".speeder(l: List<")]),r("RouterLink",{attrs:{to:"/structure.helper-classes/-spooky-wall/"}},[r("code",[t._v("SpookyWall")])]),r("code",[t._v(">): List<")]),r("RouterLink",{attrs:{to:"/structure.helper-classes/-spooky-wall/"}},[r("code",[t._v("SpookyWall")])]),r("code",[t._v(">")])],1)])])])])}),[],!1,null,null,null);e.default=s.exports}}]);