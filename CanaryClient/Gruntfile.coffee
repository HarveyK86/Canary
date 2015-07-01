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

    grunt.initConfig(
        pkg: grunt.file.readJSON("package.json")

        #grunt-contrib-clean
        clean:

            build:
                src: ["build/**"]

            coffee_build:
                src: ["build/coffee/**"]

        #grunt-contrib-coffee
        coffee:

            convert_to_js:
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

            minify_css:
                files: "build/css/canary.min.css": ["build/css/canary.css"]

        #grunt-contrib-uglify
        uglify:

            minify_js:
                files: "build/js/canary.min.js": ["build/js/canary.js"]

        #grunt-contrib-watch
        watch_files:

            coffee:
                files: ["src/coffee/**/*.coffee"]
                tasks: ["concat:coffee",
                        "coffee:convert_to_js",
                        "clean:coffee_build",
                        "concat:js",
                        "uglify:minify_js"]

            css:
                files: ["src/css/**/*.css"]
                tasks: ["concat:css",
                        "cssmin:minify_css"]

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
                    storeOutputTo: "diff"
                    postProcessOutput: (output) ->
                        output.trim()

            git_rev_parse:
                command: "rev-parse --short HEAD"
                options:
                    storeOutputTo: "hash"
                    postProcessOutput: (output) ->
                        output.trim()

        #grunt-war
        war:

           build:
                options:
                    war_dist_folder: "build"
                    war_name: "canary-client"
                    webxml_welcome: "index.html"
                    webxml_display_name: "Canary Client v<%= pkg.canary.version %>"
                files: [
                    expand: true
                    cwd: "build"
                    src: ["**"]
                    dest: ""
                ]
    )

    grunt.registerTask("war:parse_version", "", () ->
        version =
            number: grunt.config("pkg.version")
        hash = grunt.config("hash")
        diff = grunt.config("diff")
        grunt.config.set("pkg.canary.version", if diff == "" then version.number + "." + hash else version.number + "." + hash + " (development)")
    )

    grunt.registerTask("war:build_versioned", ["gta:git_diff",
                                               "gta:git_rev_parse",
                                               "war:parse_version",
                                               "war:build"])

    grunt.registerTask("build", ["clean:build", 
                                 "concat:coffee",
                                 "coffee:convert_to_js",
                                 "clean:coffee_build",
                                 "concat:js",
                                 "uglify:minify_js",
                                 "concat:css",
                                 "cssmin:minify_css",
                                 "copy:fonts",
                                 "copy:html",
                                 "copy:tpl",
                                 "war:build_versioned"])

    grunt.renameTask("watch", "watch_files")
    grunt.registerTask("watch", ["build",
                                 "watch_files"])