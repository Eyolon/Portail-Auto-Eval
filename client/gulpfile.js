(function() {
    'use strict';

    /* Chargement de GULP et des plugins */
    var gulp = require('gulp');
    var plugins = require('gulp-load-plugins')({
        pattern: ['gulp-*', 'gulp.*', 'jshint-*', 'htmlhint-*'],
		rename: {
            'gulp-ng-annotate': 'ngAnnotate'
        },
        lazy: true
    });
    
    /* Filtre de certains types de fichiers */
    var filterJS = plugins.filter(['*.js', '**/*.js'], { restore: true });
    
    var pathsSrc = {
        //html: ['app/index.html', 'app/**/*.html'],
        html: ['app/index.html', 'app/**/*.html', '!app/js/**/*.*', '!app/pdfjs/**/*.*'],
        js: ['app/*.js', 'app/**/*.js'],
        css: ['assets/css/*.css'],
        less: ['assets/less/*.less'],
        img: ['assets/images/*.ico', 'assets/img/*.png', 'assets/img/*.jpeg', 'assets/img/*.gif'],
        sound: ['assets/sounds/*.*'],
        favicon: ['assets/images/favicon.ico'],
        fonts: ['assets/fonts/*']
    };
    var pathsDest = {
        html: '../public/app',
        js: '../public/app',
        css: '../public/assets/css',
        img: '../public/assets/images',
        favicon: '../public/app',
        sound: '../public/assets/sounds',
        bowerJs: '../public/app',
        bower: '../public/bower_components',
        fonts: '../public/assets/fonts'
    };
    
    /* Gestion des erreurs */
    function handleError(err) {
        plugins.fail();
        this.emit('end');
    }
    
    /* Copie de fichiers */
    function copy(sources, destination) {
        return gulp.src(sources)
			.pipe(plugins.newer(destination))
            .pipe(gulp.dest(destination))
            .on("error", plugins.notify.onError({
                title:"Application Gulp Error",
                message:"<%= error.message %>"
            }));
    }
    
    /* Ajout notification fin de tâche */
    function functionWithNotification(functionToExecute, title, message, showOnlyForLast) {
        return function () {
            functionToExecute()
                .pipe(plugins.notify({
                    title: title,
                    message: message,
                    onLast: showOnlyForLast
                }));
        };
    }
    
    /* HTML */
    function hasHtmlHintErrors(file) {
        return !file.htmlhint.success;
    }
    function testHTML(sources, destination) {
        return gulp.src(sources)
            .pipe(plugins.newer(destination))
            .pipe(plugins.htmlhint('.htmlhintrc'))
            .pipe(plugins.htmlhint.reporter('htmlhint-stylish'))
            .pipe(plugins.htmlhint.failReporter({ suppress: true }))
            .on("error", handleError)
            .on("error", plugins.notify.onError({
                title:"Application Gulp Error",
                message:"<%= error.message %>"
            }));
    }
    function htmlhint() {
        return testHTML(pathsSrc.html, pathsDest.html)
            .pipe(plugins.if(hasHtmlHintErrors, plugins.fail("Error", true)));
    }
    function htmlhintWithNotification() {
        return testHTML(pathsSrc.html, pathsDest.html)
            .pipe(plugins.if(hasHtmlHintErrors, plugins.fail("Error", true), plugins.notify({
                title: "GULP HTMLHINT",
                message: "Application HTML checked successfully.",
                onLast: true
            })));
    }
    function html() {
        return testHTML(pathsSrc.html, pathsDest.html)
			.pipe(plugins.newer(pathsDest.html))
            .pipe(gulp.dest(pathsDest.html))
            .on("error", plugins.notify.onError({
                title:"Application Gulp Error",
                message:"<%= error.message %>"
            }));
    }
    function htmlMin() {
        return gulp.src(pathsSrc.html)
			.pipe(plugins.newer(pathsDest.html))
            .pipe(plugins.htmlmin({collapseWhitespace: true, minifyCSS: true, minifyJS: true, removeComments: true, removeCommentsFromCDATA: true}))
            .pipe(gulp.dest(pathsDest.html));
    }
    /* FIN HTML */
    
    /* JS */
    function testJS(sources) {
        return gulp.src(sources)
			.pipe(plugins.newer(pathsDest.js))
            .pipe(plugins.jshint('.jshintrc'))
            .pipe(plugins.jshint.reporter('jshint-stylish'))
            .pipe(plugins.jshint.reporter('fail'))
            .on("error", handleError)
            .on("error", plugins.notify.onError({
                title:"Application Gulp Error",
                message:"<%= error.message %>"
            }));
    }
    function jshint() {
        return testJS(pathsSrc.js)
            .pipe(plugins.notify({
                title:"GULP JSHINT",
                message: "Application JS checked successfully.",
                onLast: true
        }));
    }
    function js() {
        return testJS(pathsSrc.js)
            .pipe(plugins.sourcemaps.init())
            .pipe(plugins.concat('sum.js'))
            .pipe(plugins.sourcemaps.write())
            .pipe(gulp.dest(pathsDest.js));
    }
    function jsMin() {
        return testJS(pathsSrc.js)
			.pipe(plugins.ngAnnotate())
            .pipe(plugins.concat('sum.js'))
            .pipe(plugins.uglify())
            .pipe(gulp.dest(pathsDest.js));
    }
    /* FIN JS */
    
    /* CSS */
    function css() {
        return copy(pathsSrc.css, pathsDest.css);
    }
    function cssMin() {
        return gulp.src(pathsSrc.css)
			.pipe(plugins.newer(pathsDest.css))
            .pipe(plugins.cssnano())
            .pipe(gulp.dest(pathsDest.css))
            .on("error", plugins.notify.onError({
                title:"Application Gulp Error",
                message:"<%= error.message %>"
            }));
    }
    /* FIN CSS */
    
    /* IMAGES */
    function img() {
        return copy(pathsSrc.img, pathsDest.img);
    }
    function favicon() {
        return copy(pathsSrc.favicon, pathsDest.favicon);
    }
    /* FIN IMAGES*/
    
    /* SOUNDS */
    function sound() {
        return copy(pathsSrc.sound, pathsDest.sound);
    }
    /* FIN SOUNDS*/
    
    /* BOWER */
    function bower() {
        return gulp.src('./bower.json')
            .pipe(plugins.mainBowerFiles({
                overrides: {
                    bootstrap: {
                        main: [
                            './dist/js/bootstrap.min.js',
                            './dist/css/*.min.css',
                            './dist/fonts/*.*'
                        ]
                    },
                    nvd3: {
                        main: [
                            './build/nv.d3.min.js',
                            './build/nv.d3.min.css'
                        ]
                    },
                    'angular-loading-bar': {
                        main: [
                            './build/loading-bar.min.js',
                            './build/loading-bar.min.css'
                        ]
                    },
                    moment: {
                        main: [
                            './min/moment-with-locales.min.js'
                        ]
                    }
                }
            }))
            .pipe(filterJS)
            .pipe(plugins.concat('bower.js'))
            .pipe(gulp.dest(pathsDest.bowerJs))
            .pipe(filterJS.restore)
            .pipe(gulp.dest(pathsDest.bower));
    }
    /* FIN BOWER */
    
    /* LESS */
    function handleLess(sources) {
        return gulp.src(sources)
            .pipe(plugins.rename({suffix: ".min"}))
            .pipe(plugins.less())
            .on("error", handleError)
            .on("error", plugins.notify.onError({
                title:"Application Gulp Error",
                message:"<%= error.message %>"
            }));
    }
    function less() {
        return handleLess(pathsSrc.less)
            .pipe(gulp.dest(pathsDest.css));
    }
    function lessMin() {
        return handleLess(pathsSrc.less)
            .pipe(plugins.cssnano())
            .pipe(gulp.dest(pathsDest.css));
    }
    /* FIN LESS */
    
    /* TASKS */
    gulp.task('htmlhint', htmlhintWithNotification);
    gulp.task('html', functionWithNotification(html, "GULP HTML", "Application HTML copied successfully.", true));
    gulp.task('htmlNoNotification', html);
    gulp.task('htmlmin', functionWithNotification(htmlMin, "GULP HTML", "Application HTML compressed and copied successfully.", true));
    gulp.task('htmlminNoNotification', htmlMin);
    
    gulp.task('jshint', jshint);
    gulp.task('js', functionWithNotification(js, "GULP JS", "Application JS concat and copied successfully."));
    gulp.task('jsNoNotification', js);
    gulp.task('jsmin', functionWithNotification(jsMin, "GULP JS", "Application JS concat, compressed and copied successfully."));
    gulp.task('jsminNoNotification', jsMin);
    
    gulp.task('less', less);
    gulp.task('lessmin', lessMin);
    
    gulp.task('css', css);
    gulp.task('cssmin', cssMin);
    
    gulp.task('bowerNoNotification', bower);
    gulp.task('bower', functionWithNotification(bower, "Gulp BOWER", "Application BOWER copied successfully.", true));
    
    gulp.task('imgNoNotification', img);
    gulp.task('faviconNoNotification', favicon);
    gulp.task('soundNoNotification', sound);
    
    gulp.task('compile', ['htmlNoNotification', 'jsNoNotification', 'less', 'imgNoNotification', 'faviconNoNotification', 'css', 'soundNoNotification', 'bowerNoNotification'], function notifyCompile() {
        return gulp.src('.').pipe(plugins.notify({
                title:"GULP Compile",
                message: "Application Compile completed successfully."
        }));
    });
    gulp.task('build', ['htmlminNoNotification', 'jsminNoNotification', 'lessmin', 'imgNoNotification', 'faviconNoNotification', 'cssmin', 'soundNoNotification', 'bowerNoNotification']);
    /* FIN TASKS */
    
    function watch() {
        gulp.watch(pathsSrc.html, ['html']);
        gulp.watch(pathsSrc.js, ['js']);
        gulp.watch(pathsSrc.less, ['less']);
        gulp.watch(pathsSrc.css, ['css']);
    }
    
    gulp.task('default', ['compile', 'watch']);
    gulp.task('watch', watch);
})();