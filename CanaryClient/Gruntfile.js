module.exports = function(grunt) {
    grunt.registerTask("default", ["build"]);
    grunt.loadNpmTasks("grunt-contrib-clean");
    grunt.loadNpmTasks("grunt-contrib-coffee");
    grunt.loadNpmTasks("grunt-contrib-concat");
    grunt.loadNpmTasks("grunt-contrib-copy");
    grunt.loadNpmTasks("grunt-contrib-cssmin");
    grunt.loadNpmTasks("grunt-contrib-uglify");
    grunt.loadNpmTasks("grunt-contrib-watch");
    grunt.loadNpmTasks("grunt-war");
    grunt.initConfig({
        pkg: grunt.file.readJSON("package.json"),
        clean: {
            clean: {
                src: ["build/**"]
            },
            prune: {
                src: ["build/coffee/**"]
            }
        },
        concat: {
            coffee: {
                src: ["src/coffee/**/*.coffee"],
                dest: "build/coffee/canary.coffee"
            },
            css: {
                src: ["bower_components/bootstrap/dist/css/bootstrap.css", "src/css/**/*.css"],
                dest: "build/css/canary.css"
            },
            js: {
                src: ["bower_components/angular/angular.js",
                      "bower_components/angular-resource/angular-resource.js",
                      "bower_components/angular-route/angular-route.js",
                      "build/js/canary.js"],
                dest: "build/js/canary.js"
            }
        },
        coffee: {
            run: {
                files: {
                    "build/js/canary.js": ["build/coffee/canary.coffee"]
                }
            }
        },
        cssmin: {
            run: {
                files: {
                    "build/css/canary.min.css": ["build/css/canary.css"]
                }
            }
        },
        uglify: {
            run: {
                files: {
                    "build/js/canary.min.js": ["build/js/canary.js"]
                }
            }
        },
        copy: {
            html: {
                src: ["src/html/*.html"],
                dest: "build/",
                expand: true,
                flatten: true
            },
            tpl: {
                src: ["src/html/tpl/*.tpl.html"],
                dest: "build/tpl/",
                expand: true,
                flatten: true
            },
            fonts: {
                src: ["bower_components/bootstrap/dist/fonts/*"],
                dest: "build/fonts/",
                expand: true,
                flatten: true
            }
        },
        run_watch: {
            coffee: {
                files: ["src/coffee/**/*.coffee"],
                tasks: ["concat:coffee", "coffee", "concat:js", "uglify"]
            },
            css: {
                files: ["src/css/**/*.css"],
                tasks: ["concat:css", "cssmin"]
            },
            html: {
                files: ["src/html/*.html"],
                tasks: ["copy:html"]
            },
            tpl: {
                files: ["src/html/tpl/*.tpl.html"],
                tasks: ["copy:tpl"]
            }
        },
        war: {
            run: {
                options: {
                    war_dist_folder: "build",
                    war_name: "canary-client",
                    webxml_welcome: "index.html",
                    webxml_display_name: "Canary Client"
                },
                files: [{
                    expand: true,
                    cwd: "build",
                    src: ["**"],
                    dest: ""
                }]
            }
        }
    });
    grunt.registerTask("build", ["clean:clean", "concat:coffee", "coffee", "concat:js", "uglify", "concat:css", "cssmin", "copy", "clean:prune", "war"]);
    grunt.renameTask("watch", "run_watch");
    grunt.registerTask("watch", ["build", "run_watch"]);
};