module.exports = function(grunt) {

	grunt.registerTask("default", ["build"]);
	grunt.loadNpmTasks("grunt-contrib-clean");
	grunt.loadNpmTasks("grunt-contrib-concat");
	grunt.loadNpmTasks("grunt-contrib-copy");
	grunt.loadNpmTasks("grunt-contrib-cssmin");
	grunt.loadNpmTasks("grunt-contrib-uglify");
	grunt.loadNpmTasks("grunt-contrib-watch");
	grunt.loadNpmTasks("grunt-war");

	grunt.initConfig({

		pkg: grunt.file.readJSON("package.json"),

		clean: {

			run: {
				src: ["build/**"]
			}
		},

		concat: {

			concat_css: {

				src: ["bower_components/bootstrap/dist/css/bootstrap.css", "src/css/**/*.css"],
				dest: "build/css/canary.css"
			},

			concat_js: {

				src: ["bower_components/angular/angular.js", "bower_components/angular-resource/angular-resource.js", "src/js/**/*.js"],
				dest: "build/js/canary.js"
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

			copy_html: {

				src: ["src/html/**/*.html"],
				dest: "build/",
				expand: true,
				flatten: true
			},

			copy_fonts: {

				src: ["bower_components/bootstrap/dist/fonts/*"],
				dest: "build/fonts/",
				expand: true,
				flatten: true
			}
		},

		run_watch: {

			run: {

				files: ["src/**/*"],
				tasks: ["concat", "cssmin", "uglify", "copy"]
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

	grunt.registerTask("build", ["clean", "concat", "cssmin", "uglify", "copy", "war"]);

	grunt.renameTask("watch", "run_watch");
	grunt.registerTask("watch", ["build", "run_watch"]);
	
};
