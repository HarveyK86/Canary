module.exports = (grunt) ->
    grunt.registerTask("default", ["build"])
    grunt.loadNpmTasks("grunt-contrib-clean")
    grunt.loadNpmTasks("grunt-contrib-coffee")
    grunt.loadNpmTasks("grunt-contrib-concat")
    grunt.loadNpmTasks("grunt-contrib-copy")
    grunt.loadNpmTasks("grunt-contrib-cssmin")
    grunt.loadNpmTasks("grunt-contrib-uglify")
    grunt.loadNpmTasks("grunt-contrib-watch")
    grunt.loadNpmTasks("grunt-git-them-all")
    grunt.loadNpmTasks("grunt-war")

    self = this

    self.hash = ""
    self.diff = ""

    grunt.initConfig(
        pkg: grunt.file.readJSON("package.json")

        #grunt-contrib-clean
        clean:

            build:
                src: ["build/**"]

            coffee:
                src: ["build/coffee/**"]

        #grunt-contrib-coffee
        coffee:

            convert:
                files: "build/js/canary.js": ["build/coffee/canary.coffee"]

        #grunt-contrib-concat
        concat:

            coffee:
                src: ["src/coffee/**/*.coffee"]
                dest: "build/coffee/canary.coffee"

            css:
                src: ["bower_components/bootstrap/dist/css/bootstrap.css",
                      "src/css/**/*.css"]
                dest: "build/css/canary.css"

            js:
                src: ["bower_components/angular/angular.js",
                      "bower_components/angular-native-dragdrop/draganddrop.js",
                      "bower_components/angular-resource/angular-resource.js",
                      "bower_components/angular-route/angular-route.js",
                      "build/js/canary.js"]
                dest: "build/js/canary.js"

        #grunt-contrib-copy
        copy:

            fonts:
                src: ["bower_components/bootstrap/dist/fonts/*"]
                dest: "build/fonts/"
                expand: true
                flatten: true

            html:
                src: ["src/html/*.html"]
                dest: "build/"
                expand: true
                flatten: true

            tpl:
                src: ["src/html/tpl/*.tpl.html"]
                dest: "build/tpl/"
                expand: true
                flatten: true

        #grunt-contrib-cssmin
        cssmin:

            run:
                files: "build/css/canary.min.css": ["build/css/canary.css"]

        #grunt-contrib-uglify
        uglify:

            jsmin:
                files: "build/js/canary.min.js": ["build/js/canary.js"]

        #grunt-contrib-watch
        run_watch:

            coffee:
                files: ["src/coffee/**/*.coffee"]
                tasks: ["concat:coffee",
                        "coffee:convert",
                        "concat:js",
                        "uglify:jsmin"]

            css:
                files: ["src/css/**/*.css"]
                tasks: ["concat:css",
                        "cssmin:run"]

            html:
                files: ["src/html/*.html"]
                tasks: ["copy:html"]

            tpl:
                files: ["src/html/tpl/*.tpl.html"]
                tasks: ["copy:tpl"]

        #grunt-git-them-all
        gta:

            git_diff:
                command: "diff --shortstat HEAD"
                options:
                    storeOutputTo: "self.diff"

            git_rev_parse:
                command: "rev-parse --short HEAD"
                options:
                    storeOutputTo: "self.hash"

        #grunt-war
        war:

            build:
                options:
                    war_dist_folder: "build"
                    war_name: "canary-client"
                    webxml_welcome: "index.html"
                    webxml_display_name: "Canary Client v" + if "<%= self.diff %>" == "" then "<%= pkg.version %>.<%= self.hash %>" else "<%= pkg.version %>.<%= self.hash %> (development)"
                files: [
                    expand: true
                    cwd: "build"
                    src: ["**"]
                    dest: ""
                ]
    )

    grunt.registerTask("build", ["clean:build", 
                                 "concat:coffee",
                                 "coffee:convert",
                                 "clean:coffee",
                                 "concat:js",
                                 "uglify:jsmin",
                                 "concat:css",
                                 "cssmin:run",
                                 "copy:fonts",
                                 "copy:html",
                                 "copy:tpl",
                                 "gta:git_diff"
                                 "gta:git_rev_parse",
                                 "war:build"])

    grunt.renameTask("watch", "run_watch")
    grunt.registerTask("watch", ["build",
                                 "run_watch"])