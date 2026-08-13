import * as THREE from "three";


document.addEventListener(
    "DOMContentLoaded",
    () => {

        /* =====================================================
           WEBSITE ELEMENTS
        ===================================================== */

        const header =
            document.querySelector(
                "[data-header]"
            );

        const menuButton =
            document.querySelector(
                "[data-menu-button]"
            );

        const mobileMenu =
            document.querySelector(
                "[data-mobile-menu]"
            );

        const backToTop =
            document.querySelector(
                "[data-back-top]"
            );

        const year =
            document.querySelector(
                "[data-current-year]"
            );

        const revealElements =
            document.querySelectorAll(
                "[data-reveal]"
            );

        const internalLinks =
            document.querySelectorAll(
                'a[href^="#"]'
            );


        /* =====================================================
           CURRENT YEAR
        ===================================================== */

        if (year) {

            year.textContent =
                new Date()
                    .getFullYear();

        }


        /* =====================================================
           MOBILE MENU
        ===================================================== */

        const closeMenu =
            () => {

                if (
                    !menuButton ||
                    !mobileMenu
                ) {
                    return;
                }

                mobileMenu
                    .classList
                    .remove(
                        "is-open"
                    );

                menuButton
                    .setAttribute(
                        "aria-expanded",
                        "false"
                    );

                document.body
                    .classList
                    .remove(
                        "menu-open"
                    );
            };


        if (
            menuButton &&
            mobileMenu
        ) {

            menuButton
                .addEventListener(
                    "click",
                    () => {

                        const open =
                            mobileMenu
                                .classList
                                .toggle(
                                    "is-open"
                                );

                        menuButton
                            .setAttribute(
                                "aria-expanded",
                                String(open)
                            );

                        document.body
                            .classList
                            .toggle(
                                "menu-open",
                                open
                            );
                    }
                );

        }


        document.addEventListener(
            "keydown",
            event => {

                if (
                    event.key ===
                    "Escape"
                ) {
                    closeMenu();
                }
            }
        );


        /* =====================================================
           SMOOTH SCROLL
        ===================================================== */

        internalLinks.forEach(
            link => {

                link.addEventListener(
                    "click",
                    event => {

                        const href =
                            link.getAttribute(
                                "href"
                            );

                        if (
                            !href ||
                            href === "#"
                        ) {
                            return;
                        }

                        let target;

                        try {

                            target =
                                document
                                    .querySelector(
                                        href
                                    );

                        } catch {

                            return;

                        }


                        if (!target) {
                            return;
                        }


                        event.preventDefault();


                        const headerHeight =
                            header
                                ? header.offsetHeight
                                : 0;


                        const targetY =
                            target
                                .getBoundingClientRect()
                                .top
                            +
                            window.scrollY
                            -
                            headerHeight
                            -
                            10;


                        window.scrollTo({
                            top:
                                targetY,

                            behavior:
                                "smooth"
                        });


                        closeMenu();
                    }
                );

            }
        );


        /* =====================================================
           HEADER
        ===================================================== */

        const updateHeader =
            () => {

                if (!header) {
                    return;
                }

                header.classList.toggle(
                    "is-scrolled",
                    window.scrollY > 24
                );
            };


        updateHeader();


        window.addEventListener(
            "scroll",
            updateHeader,
            {
                passive: true
            }
        );


        /* =====================================================
           BACK TO TOP
        ===================================================== */

        if (backToTop) {

            const updateBackToTop =
                () => {

                    backToTop
                        .classList
                        .toggle(
                            "visible",
                            window.scrollY >
                            650
                        );
                };


            updateBackToTop();


            window.addEventListener(
                "scroll",
                updateBackToTop,
                {
                    passive: true
                }
            );


            backToTop
                .addEventListener(
                    "click",
                    () => {

                        window.scrollTo({
                            top: 0,
                            behavior:
                                "smooth"
                        });
                    }
                );
        }


        /* =====================================================
           REVEAL SECTIONS
        ===================================================== */

        const reducedMotion =
            window.matchMedia(
                "(prefers-reduced-motion: reduce)"
            ).matches;


        if (
            reducedMotion ||
            !(
                "IntersectionObserver"
                in window
            )
        ) {

            revealElements
                .forEach(
                    element => {

                        element
                            .classList
                            .add(
                                "is-visible"
                            );
                    }
                );

        } else {

            const revealObserver =
                new IntersectionObserver(
                    (
                        entries,
                        observer
                    ) => {

                        entries
                            .forEach(
                                entry => {

                                    if (
                                        !entry
                                            .isIntersecting
                                    ) {
                                        return;
                                    }

                                    entry
                                        .target
                                        .classList
                                        .add(
                                            "is-visible"
                                        );

                                    observer
                                        .unobserve(
                                            entry.target
                                        );
                                }
                            );
                    },
                    {
                        threshold:
                            0.12,

                        rootMargin:
                            "0px 0px -45px 0px"
                    }
                );


            revealElements
                .forEach(
                    element => {

                        revealObserver
                            .observe(
                                element
                            );
                    }
                );
        }


        /* =====================================================
           ACTIVE NAVIGATION
        ===================================================== */

        const sections =
            document.querySelectorAll(
                "main section[id]"
            );

        const desktopLinks =
            document.querySelectorAll(
                '.desktop-nav a[href^="#"]'
            );


        if (
            sections.length &&
            "IntersectionObserver"
            in window
        ) {

            const sectionObserver =
                new IntersectionObserver(
                    entries => {

                        entries.forEach(
                            entry => {

                                if (
                                    !entry
                                        .isIntersecting
                                ) {
                                    return;
                                }

                                const id =
                                    entry.target.id;


                                desktopLinks
                                    .forEach(
                                        link => {

                                            link.classList
                                                .toggle(
                                                    "is-active",

                                                    link
                                                        .getAttribute(
                                                            "href"
                                                        )
                                                    ===
                                                    `#${id}`
                                                );
                                        }
                                    );
                            }
                        );
                    },
                    {
                        rootMargin:
                            "-35% 0px -55% 0px"
                    }
                );


            sections.forEach(
                section => {

                    sectionObserver
                        .observe(
                            section
                        );
                }
            );

        }


        /* =====================================================
           RESIZE MENU RESET
        ===================================================== */

        window.addEventListener(
            "resize",
            () => {

                if (
                    window.innerWidth >
                    900
                ) {
                    closeMenu();
                }
            }
        );


        /* =====================================================
           3D WORLD GLOBE
        ===================================================== */

        const canvas =
            document.getElementById(
                "world-globe-canvas"
            );

        const loading =
            document.getElementById(
                "globe-loading"
            );


        if (!canvas) {
            return;
        }


        /* =====================================================
           THREE SCENE
        ===================================================== */

        const scene =
            new THREE.Scene();


        const camera =
            new THREE
                .PerspectiveCamera(
                    35,
                    1,
                    0.1,
                    100
                );


        camera.position.set(
            0,
            0,
            3.25
        );


        /* =====================================================
           RENDERER
        ===================================================== */

        let renderer;


        try {

            renderer =
                new THREE.WebGLRenderer({
                    canvas:
                        canvas,

                    alpha:
                        true,

                    antialias:
                        true,

                    powerPreference:
                        "high-performance"
                });

        } catch (error) {

            console.error(
                "3D globe could not start:",
                error
            );

            if (loading) {
                loading.innerHTML =
                    "3D globe unavailable";
            }

            return;
        }


        renderer.setPixelRatio(
            Math.min(
                window.devicePixelRatio ||
                1,
                2
            )
        );


        renderer.setClearColor(
            0x000000,
            0
        );


        renderer.outputColorSpace =
            THREE.SRGBColorSpace;


        /* =====================================================
           EARTH GROUP
        ===================================================== */

        const earthGroup =
            new THREE.Group();


        earthGroup.rotation.x =
            THREE.MathUtils
                .degToRad(
                    -8
                );


        earthGroup.rotation.z =
            THREE.MathUtils
                .degToRad(
                    -23.4
                );


        scene.add(
            earthGroup
        );


        /* =====================================================
           EARTH GEOMETRY
        ===================================================== */

        const earthGeometry =
            new THREE
                .SphereGeometry(
                    1,
                    96,
                    96
                );


        /* =====================================================
           TEXTURE
        ===================================================== */

        const textureLoader =
            new THREE.TextureLoader();


        /*
         * TEMPORARY EARTH TEXTURE.
         *
         * Later we can download this into:
         *
         * docs/assets/images/earth-map.jpg
         *
         * and then replace the URL with:
         *
         * assets/images/earth-map.jpg
         */

        const textureUrl =
            "https://threejs.org/examples/textures/planets/earth_atmos_2048.jpg";


        textureLoader.load(
            textureUrl,

            texture => {

                texture.colorSpace =
                    THREE.SRGBColorSpace;


                texture.anisotropy =
                    Math.min(
                        8,
                        renderer
                            .capabilities
                            .getMaxAnisotropy()
                    );


                /* =================================================
                   EARTH MATERIAL
                ================================================= */

                const earthMaterial =
                    new THREE
                        .MeshPhongMaterial({
                            map:
                                texture,

                            color:
                                0xffffff,

                            shininess:
                                10,

                            specular:
                                new THREE.Color(
                                    0x336c8f
                                )
                        });


                const earth =
                    new THREE.Mesh(
                        earthGeometry,
                        earthMaterial
                    );


                earthGroup.add(
                    earth
                );


                /* =================================================
                   CLOUD-LIKE SOFT SHELL
                ================================================= */

                const atmosphereGeometry =
                    new THREE
                        .SphereGeometry(
                            1.025,
                            96,
                            96
                        );


                const atmosphereMaterial =
                    new THREE
                        .MeshPhongMaterial({
                            color:
                                0x64b7ff,

                            transparent:
                                true,

                            opacity:
                                0.055,

                            side:
                                THREE.FrontSide,

                            depthWrite:
                                false
                        });


                const atmosphere =
                    new THREE.Mesh(
                        atmosphereGeometry,
                        atmosphereMaterial
                    );


                earthGroup.add(
                    atmosphere
                );


                /* =================================================
                   ATMOSPHERE GLOW
                ================================================= */

                const glowGeometry =
                    new THREE
                        .SphereGeometry(
                            1.075,
                            64,
                            64
                        );


                const glowMaterial =
                    new THREE
                        .ShaderMaterial({
                            uniforms: {
                                glowColor: {
                                    value:
                                        new THREE
                                            .Color(
                                                0x168cff
                                            )
                                }
                            },

                            vertexShader: `
                                varying vec3 vNormal;

                                void main() {
                                    vNormal =
                                        normalize(
                                            normalMatrix *
                                            normal
                                        );

                                    gl_Position =
                                        projectionMatrix *
                                        modelViewMatrix *
                                        vec4(
                                            position,
                                            1.0
                                        );
                                }
                            `,

                            fragmentShader: `
                                varying vec3 vNormal;
                                uniform vec3 glowColor;

                                void main() {

                                    float intensity =
                                        pow(
                                            0.72 -
                                            dot(
                                                vNormal,
                                                vec3(
                                                    0.0,
                                                    0.0,
                                                    1.0
                                                )
                                            ),
                                            2.1
                                        );

                                    gl_FragColor =
                                        vec4(
                                            glowColor,
                                            intensity *
                                            0.45
                                        );
                                }
                            `,

                            side:
                                THREE.BackSide,

                            blending:
                                THREE.AdditiveBlending,

                            transparent:
                                true,

                            depthWrite:
                                false
                        });


                const glow =
                    new THREE.Mesh(
                        glowGeometry,
                        glowMaterial
                    );


                earthGroup.add(
                    glow
                );


                if (loading) {

                    loading
                        .classList
                        .add(
                            "is-hidden"
                        );
                }

            },

            undefined,

            error => {

                console.error(
                    "Earth texture error:",
                    error
                );


                /*
                 * FALLBACK:
                 * Even if texture loading fails,
                 * display a blue 3D globe.
                 */

                const fallbackMaterial =
                    new THREE
                        .MeshPhongMaterial({
                            color:
                                0x096bb5,

                            shininess:
                                18
                        });


                const fallbackEarth =
                    new THREE.Mesh(
                        earthGeometry,
                        fallbackMaterial
                    );


                earthGroup.add(
                    fallbackEarth
                );


                if (loading) {

                    loading
                        .classList
                        .add(
                            "is-hidden"
                        );
                }
            }
        );


        /* =====================================================
           LIGHTING
        ===================================================== */

        const ambientLight =
            new THREE
                .AmbientLight(
                    0x769fc3,
                    1.15
                );


        scene.add(
            ambientLight
        );


        const sunlight =
            new THREE
                .DirectionalLight(
                    0xffffff,
                    3.15
                );


        sunlight.position.set(
            -3.5,
            2.7,
            4.5
        );


        scene.add(
            sunlight
        );


        const blueRimLight =
            new THREE
                .DirectionalLight(
                    0x167dff,
                    1.35
                );


        blueRimLight
            .position
            .set(
                4,
                -1,
                -3
            );


        scene.add(
            blueRimLight
        );


        /* =====================================================
           STAR FIELD
        ===================================================== */

        const starsCount =
            window.innerWidth < 620
                ? 160
                : 340;


        const starPositions =
            new Float32Array(
                starsCount *
                3
            );


        for (
            let i = 0;
            i < starsCount;
            i++
        ) {

            const offset =
                i * 3;


            starPositions[offset] =
                (
                    Math.random() -
                    .5
                ) * 11;


            starPositions[offset + 1] =
                (
                    Math.random() -
                    .5
                ) * 11;


            starPositions[offset + 2] =
                (
                    Math.random() -
                    .5
                ) * 6;
        }


        const starsGeometry =
            new THREE
                .BufferGeometry();


        starsGeometry
            .setAttribute(
                "position",

                new THREE
                    .BufferAttribute(
                        starPositions,
                        3
                    )
            );


        const starsMaterial =
            new THREE
                .PointsMaterial({
                    color:
                        0xffffff,

                    size:
                        .012,

                    opacity:
                        .32,

                    transparent:
                        true,

                    depthWrite:
                        false
                });


        const starField =
            new THREE.Points(
                starsGeometry,
                starsMaterial
            );


        scene.add(
            starField
        );


        /* =====================================================
           RESPONSIVE CANVAS
        ===================================================== */

        const resizeRenderer =
            () => {

                const width =
                    canvas
                        .clientWidth;

                const height =
                    canvas
                        .clientHeight;


                if (
                    width === 0 ||
                    height === 0
                ) {
                    return;
                }


                renderer.setSize(
                    width,
                    height,
                    false
                );


                camera.aspect =
                    width /
                    height;


                camera
                    .updateProjectionMatrix();
            };


        resizeRenderer();


        window.addEventListener(
            "resize",
            resizeRenderer,
            {
                passive: true
            }
        );


        /* =====================================================
           INTERACTIVE ROTATION
        ===================================================== */

        let dragging =
            false;


        let lastPointerX =
            0;


        let lastPointerY =
            0;


        let rotationVelocityX =
            0;


        let rotationVelocityY =
            0;


        canvas.addEventListener(
            "pointerdown",
            event => {

                dragging =
                    true;


                lastPointerX =
                    event.clientX;


                lastPointerY =
                    event.clientY;


                canvas
                    .setPointerCapture(
                        event.pointerId
                    );
            }
        );


        canvas.addEventListener(
            "pointermove",
            event => {

                if (!dragging) {
                    return;
                }


                const dx =
                    event.clientX -
                    lastPointerX;


                const dy =
                    event.clientY -
                    lastPointerY;


                rotationVelocityX =
                    dx *
                    .004;


                rotationVelocityY =
                    dy *
                    .003;


                earthGroup
                    .rotation
                    .y +=
                    rotationVelocityX;


                earthGroup
                    .rotation
                    .x +=
                    rotationVelocityY;


                earthGroup.rotation.x =
                    Math.max(
                        -.7,

                        Math.min(
                            .7,
                            earthGroup
                                .rotation
                                .x
                        )
                    );


                lastPointerX =
                    event.clientX;


                lastPointerY =
                    event.clientY;
            }
        );


        const finishPointer =
            event => {

                dragging =
                    false;


                if (
                    event &&
                    canvas.hasPointerCapture(
                        event.pointerId
                    )
                ) {

                    canvas
                        .releasePointerCapture(
                            event.pointerId
                        );
                }
            };


        canvas.addEventListener(
            "pointerup",
            finishPointer
        );


        canvas.addEventListener(
            "pointercancel",
            finishPointer
        );


        /* =====================================================
           ANIMATION
        ===================================================== */

        let previousFrame =
            performance.now();


        let frameId =
            null;


        let pageVisible =
            !document.hidden;


        const animate =
            time => {

                if (!pageVisible) {

                    frameId =
                        requestAnimationFrame(
                            animate
                        );

                    previousFrame =
                        time;

                    return;
                }


                const deltaTime =
                    Math.min(
                        (
                            time -
                            previousFrame
                        ) /
                        1000,
                        .05
                    );


                previousFrame =
                    time;


                if (!dragging) {

    // Slow automatic rotation on all browsers,
    // including Firefox on Ubuntu.
    earthGroup.rotation.y +=
        0.085 *
        deltaTime;

    // Keep drag inertia.
    earthGroup.rotation.y +=
        rotationVelocityX;

    earthGroup.rotation.x +=
        rotationVelocityY;

    rotationVelocityX *=
        0.92;

    rotationVelocityY *=
        0.92;
}


                if (!reducedMotion) {

                    starField
                        .rotation
                        .y +=
                        .008 *
                        deltaTime;

                }


                renderer.render(
                    scene,
                    camera
                );


                frameId =
                    requestAnimationFrame(
                        animate
                    );
            };


        frameId =
            requestAnimationFrame(
                animate
            );


        /* =====================================================
           PAUSE IF TAB IS HIDDEN
        ===================================================== */

        document.addEventListener(
            "visibilitychange",
            () => {

                pageVisible =
                    !document.hidden;
            }
        );


        /* =====================================================
           CLEAN UP
        ===================================================== */

        window.addEventListener(
            "beforeunload",
            () => {

                if (frameId) {

                    cancelAnimationFrame(
                        frameId
                    );
                }


                earthGeometry.dispose();

                starsGeometry.dispose();

                starsMaterial.dispose();

                renderer.dispose();
            }
        );

    }
);a
