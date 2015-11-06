module.exports = function (grunt) {

    // Project configuration.
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        bower_concat: {
            bower: {
                dest: '../public/app/bower.js',
                mainFiles: {
                    //'pdfjs-bower': ['dist/pdf.js', 'dist/pdf.worker.js', 'dist/compatibility.js'], // NE PAS INCLURE PDFJS DANS BOWER.js PLEASE (iframe, pas projet principal)
                    //'angular-i18n': [], //'angular-locale_fr-fr.js','angular-locale_fr-fr.js' ,'angular-locale_en.js','angular-locale_en-us.js','angular-locale_en-gb.js' ],
                    'moment': ['moment.js', 'locale/fr.js', 'locale/en-gb.js', 'locale/pt-br.js', 'locale/de.js'],
                    //'spectrum': ['spectrum.js'],
                    //'angular-spectrum-colorpicker': ['dist/angular-spectrum-colorpicker.min.js'],
                    //'angular-schema-form': ['dist/schema-form.min.js', 'dist/bootstrap-decorator.min.js'],
                    //'angular-schema-form-colorpicker': ['bootstrap-colorpicker.min.js'],
                    //'angular-ui-select': ['dist/select.min.js'],
                    //'angular-schema-form-datepicker': ['bootstrap-datepicker.min.js'],
                    'angular-smart-table': ['dist/smart-table.min.js'],
                    'grunt-contrib-jshint': [''],
                    'grunt-contrib-nodeunit': [''],
                    'grunt-contrib-watch': ['']//,
                    //'angular-drag-and-drop-lists': ['../../vendor/angular-drag-and-drop-lists.js']
                },
                dependencies: {
                    'angular': 'jquery'//,
                    //'angular-spectrum-colorpicker': 'spectrum'
                },
                exclude: [
                    'pdfjs-bower', 'angular-i18n'
                ]
            }
        },
        mainFiles: {
            'jQuery': ['src/ajax.js']
        },
        concat: {
            dev: {
                options: {
                    sourceMap: true,
                    separator: '\n\n'
                },
                src: ['app/app.js', 'app/modals.js', 'app/views.js', 'app/**/*.js'],
                dest: '../public/app/sum.js'
            },
            prod: {
                options: {
                    separator: '\n\n'
                },
                src: ['app/app.js', 'app/modals.js', 'app/views.js', 'app/**/*.js'],
                dest: '../public/app/sum.js'
            }
        },
        wrap: {
            prod: {
                src: ['<%= concat.prod.dest %>'],
                dest: '<%= concat.prod.dest %>',
                options: {
                    separator: '',
                    wrapper: ['(function wrapProd() {', '\n})();']
                }
            },
            bower: {
                src: ['<%= bower_concat.bower.dest %>'],
                dest: '<%= bower_concat.bower.dest %>',
                options: {
                    wrapper: ['(function wrapBower() {', '})();']
                }
            }
        },
        ngAnnotate: {
            options: {
                singleQuotes: true
            },
            prod: {
                files: {
                    '<%= concat.prod.dest %>': ['<%= concat.prod.dest %>']
                }
            }
        },
        uglify: {
            all: {
                files: {
                    '<%= concat.prod.dest %>': ['<%= concat.prod.dest %>'],
                    '<%= bower_concat.bower.dest %>': ['<%= bower_concat.bower.dest %>']
                }
            }
        },
        jshint: {
            all: {
                src: ['<%= concat.prod.src %>'],
                options: {
                    jshintrc: './.jshintrc.json'
                }
            },
            sum: {
                src: ['<%= concat.prod.dest %>'],
                options: {
                    jshintrc: './.jshintrc.json'
                }
            }
        },
        htmlhint: {
            all: {
                src: ['client/app/**/*.html'],
                options: {
                    htmlhintrc: './.htmlhintrc.json'
                }
            },
        },
        less: {
            prod: {
                options: {
                    cleancss: true,
                    compress: true
                },
                files: {
                    '../public/assets/css/template.min.css': 'client/assets/less/template.less'
                }
            },
            dev: {
                options: {
                    cleancss: true,
                    compress: false
                },
                files: {
                    '../public/assets/css/template.min.css': 'client/assets/less/template.less'
                }
            }
        },
        shell: {
            npmUpdate: {
                command: [
                    'npm update'
                ].join('&&')
            },
            bowerUpdate: {
                command: [
                    'bower update'
                ].join('&&')
            }
        },
        clean: {
            cleanPublic: {
                src: ['../public/'],
                options: {
                    force: true
                }
            },
            /*ATTENTION ne pas utiliser en développement*/
            cleanClient: {
                src: ['../client/'],
                options: {
                    force: true
                }
            }
        },
        copy: {
			/*js: {
                files: [
                    {*/
                        //src: ['app/**/*.js'],
                        /*dest: '../public/',
                        expand: true
                    }
                ]
            },*/
            html: {
                files: [
                    /*{
                        src: 'app/index.html',
                        dest: '../public/app/index.html'
                    },*/
                    {
                        src: ['app/**/*.html'],
                        dest: '../public/',
                        expand: true
                    }
                ]
            },
			css: {
                files: [
                    {
                        src: ['assets/css/*.css'],
                        dest: '../public/assets/css/'
                    },
                    {
                        src: ['assets/css/**/*.css'],
                        dest: '../public/',
                        expand: true
                    }
                ]
            },
            assets: {
                files: [
                    {
                        src: ['assets/images/*.*'],
                        dest: '../public/',
                        expand: true
                    },
					{
                        src: ['assets/images/**/*.*'],
                        dest: '../public/',
                        expand: true
                    },
                    {
                        src: ['assets/images/favicon.ico'],
                        dest: '../public/favicon.ico'
                    }
                ]
            },
            bowerComponentsFiles: {
                files: [
                    {
                        src: ['bower_components/moment/locale/*.js', 'bower_components/**/*.min.css', 'bower_components/angular-utils-pagination/dirPagination.tpl.html'],
                        dest: '../public/',
                        expand: true
                    }
                ]
            }
        },
        focus: {
            all: {
                exclude: [ /*'copyI18N',*/ 'classAquiwebServerJar'],
                include: ['copyHTML', 'copyCSS', 'copyImg', 'copyJSLibrarie', 'lessApp', 'jsApp', 'bowerUpdate', 'npmUpdate']
            },
            min: {
                exclude: ['copyImg', 'copyJSLibrarie', 'npmUpdate'],
                include: ['copyHTML', 'copyCSS', 'lessApp', 'jsApp', 'bowerUpdate']
            },
        },
        watch: {
            copyHTML: {
                files: ['app/index.html', 'client/app/**/*.html'],
                tasks: ['newer:copy:html', 'notify:copyHTML'],
                options: {
                    spawn: false,
                    livereload: true
                }
            },
            copyCSS: {
                files: ['client/css/*.min.css', 'client/css/modals.css', 'client/css/iconCustom.css', 'client/css/directives.css'],
                tasks: ['newer:copy:filesAquiweb', 'notify:copyCSS'],
                options: {
                    livereload: true
                }
            },
            copyImg: {
                files: ['client/favicon.ico', 'client/img/*.*'],
                tasks: ['newer:copy:filesAquiweb', 'notify:copyIMG'],
                options: {
                    livereload: true
                }
            },
            copyJSLibrarie: {
                files: ['bower_components/moment/locale/*.js', 'bower_components/**/*.min.css', 'bower_components/angular-utils-pagination/dirPagination.tpl.html'/*, 'bower_components/spectrum/spectrum.css'*/, 'client/fonts/*'/*, 'bower_components/pickadate/lib/themes/classic.css', 'bower_components/pickadate/lib/themes/classic.date.css', 'bower_components/angular-ui-select/dist/select.min.css'*/],
                tasks: ['newer:copy:bowerComponentsFiles', 'notify:copyJSLibrarie'],
                options: {
                    livereload: true
                }
            },
            lessApp: {
                files: ['client/less/*.less'],
                tasks: ['less:dev', 'notify:less'],
                options: {
                    livereload: true
                }
            },
            jsApp: {
                files: ['<%= concat.prod.src %>'],
                tasks: ['newer:jshint:all', 'concat:dev', 'wrap:prod', 'notify:jsApp'],
                options: {
                    livereload: true
                }
            },
            bowerUpdate: {
                files: ['bower.json'],
                tasks: ['shell:bowerUpdate', 'bower_concat:bower', 'notify:bowerUpdate'],
                options: {
                    livereload: true
                }
            },
            npmUpdate: {
                files: ['package.json'],
                tasks: ['shell:npmUpdate', 'notify:npmUpdate'],
                options: {
                    livereload: true
                }
            }
        },
        notify_hooks: {
            options: {
                enabled: true,
                max_jshint_notifications: 5, // maximum number of notifications from jshint output 
                title: 'WebApp', // defaults to the name in package.json, or will use project directory's name 
                success: false, // whether successful grunt executions should be notified automatically 
                duration: 5 // the duration of notification in seconds, for `notify-send only 
            }
        },
        notify: {
            devready: {
                options: {
                    title: 'devready',
                    message: '<%= pkg.name %> build finished successfully.'
                }
            },
            dist: {
                options: {
                    title: 'dist',
                    message: '<%= pkg.name %> build finished successfully.'
                }
            },
            cleanPublic: {
                options: {
                    title: 'cleanPublic',
                    message: '<%= pkg.name %> cleaned successfully.'
                }
            },
            copyHTML: {
                options: {
                    title: 'copyHTML',
                    message: '<%= pkg.name %> HTML copied successfully.'
                }
            },
            copyCSS: {
                options: {
                    title: 'copyCSS',
                    message: '<%= pkg.name %> CSS copied successfully.'
                }
            },
            copyIMG: {
                options: {
                    title: 'copyIMG',
                    message: '<%= pkg.name %> IMG copied successfully.'
                }
            },
            copyJSLibrarie: {
                options: {
                    title: 'copyJSLibrarie',
                    message: '<%= pkg.name %> JSLibraries copied successfully.'
                }
            },
            less: {
                options: {
                    title: 'less',
                    message: '<%= pkg.name %> LESS copied successfully.'
                }
            },
            jsApp: {
                options: {
                    title: 'jsApp',
                    message: '<%= pkg.name %> JS concatenated AND wraped successfully.'
                }
            },
            jsBower: {
                options: {
                    title: 'jsBower',
                    message: '<%= pkg.name %> JSBOWER concatenated AND copied successfully.'
                }
            },
            bowerUpdate: {
                options: {
                    title: 'bowerUpdate',
                    message: '<%= pkg.name %> bower updated successfully.'
                }
            },
            npmUpdate: {
                options: {
                    title: 'npmUpdate',
                    message: '<%= pkg.name %> npm updated successfully.'
                }
            }
        },
        /*concurrent: {
            devready1: {
                tasks: ['concat:dev', 'bower_concat:bower', 'less:dev', 'copy:bowerComponentsFiles', 'copy:assets', 'copy:html']
            },
            dist1: {
                tasks: ['concat:prod', 'bower_concat:bower', 'copy:html', 'less:prod', 'copy:assets', 'copy:bowerComponentsFiles']
            },
            bower1: {
                tasks: ['bower_concat:bower', 'copy:bowerComponentsFiles']
            },
        },*/
        /*protractor: {
            webapp: {
                options: {
                    configFile: 'protractor.conf.js', // Default config file
                    keepAlive: false, // If false, the grunt process stops when the test fails.
                    noColor: true, // If true, protractor will not use colors in its output.
                    args: {
                        // Arguments passed to the command
                    }
                }
            }
        },
        protractor_webdriver: {
            webapp: {
                options: {}
            }
        }*/
    });

    // Load plugins.
    require('load-grunt-tasks')(grunt);

    // Watchers Tasks
    grunt.registerTask('watch-all', 'focus:all');
    grunt.registerTask('watch-min', 'focus:min');

    // Specifics Tasks
    grunt.registerTask('html', ['htmlhint:all', 'copy:html', 'notify:copyHTML']);
    grunt.registerTask('cleaner', ['clean:cleanPublic', 'notify:cleanPublic']); //ne pas remplacer awcleaner par clean !
    grunt.registerTask('js', ['jshint:all', 'concat:dev', 'wrap:prod', 'notify:jsApp']);
    grunt.registerTask('bower', ['concurrent:bower1', 'wrap:bower', 'notify:jsBower']);
    grunt.registerTask('template', ['less:prod', 'notify:less']);

    // Full Tasks
    grunt.registerTask('devready', ['concat:dev', 'bower_concat:bower', 'copy:bowerComponentsFiles', 'copy:css'/*, 'copy:js'*/, 'copy:assets', 'copy:html', 'less:prod', 'wrap:prod', 'notify:devready']);
	grunt.registerTask('build', ['concat:prod', 'bower_concat:bower', 'copy:bowerComponentsFiles', 'copy:css'/*, 'copy:js'*/, 'copy:assets', 'copy:html', 'less:prod', 'wrap:prod', 'clean:cleanClient']);
    /*grunt.registerTask('dist', ['clean:cleanPublic', 'concurrent:dist1', 'wrap:prod', 'wrap:bower', 'ngAnnotate:prod', 'uglify:all', 'shell:playdist', 'notify:dist', 'shell:openDistPathInExplorer']);
    grunt.registerTask('distWithoutUglify', ['clean:cleanPublic', 'concurrent:dist1', 'wrap:prod', 'wrap:bower', 'ngAnnotate:prod', 'shell:playdist', 'notify:dist', 'shell:openDistPathInExplorer']);*/

    // Others Tasks
    grunt.registerTask('updateBower', ['shell:bowerUpdate', 'notify:bowerUpdate', 'concurrent:bower1', 'wrap:bower', 'notify:jsBower']);
    grunt.registerTask('updateNpm', ['shell:npmUpdate', 'notify:npmUpdate']);

    //grunt.task.run('notify_hooks');
};