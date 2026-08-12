document.addEventListener("DOMContentLoaded", () => {

    const header =
        document.querySelector("[data-header]");

    const menuButton =
        document.querySelector("[data-menu-button]");

    const mobileMenu =
        document.querySelector("[data-mobile-menu]");

    const backToTop =
        document.querySelector("[data-back-top]");

    const year =
        document.querySelector("[data-current-year]");

    const revealElements =
        document.querySelectorAll("[data-reveal]");

    const internalLinks =
        document.querySelectorAll('a[href^="#"]');


    /* =====================================================
       CURRENT YEAR
    ===================================================== */

    if (year) {
        year.textContent =
            new Date().getFullYear();
    }


    /* =====================================================
       MOBILE MENU
    ===================================================== */

    const closeMenu = () => {

        if (!menuButton || !mobileMenu) {
            return;
        }

        mobileMenu.classList.remove("is-open");

        menuButton.setAttribute(
            "aria-expanded",
            "false"
        );

        document.body.classList.remove(
            "menu-open"
        );
    };


    if (menuButton && mobileMenu) {

        menuButton.addEventListener(
            "click",
            () => {

                const isOpen =
                    mobileMenu.classList.toggle(
                        "is-open"
                    );

                menuButton.setAttribute(
                    "aria-expanded",
                    String(isOpen)
                );

                document.body.classList.toggle(
                    "menu-open",
                    isOpen
                );
            }
        );
    }


    document.addEventListener(
        "keydown",
        (event) => {

            if (event.key === "Escape") {
                closeMenu();
            }
        }
    );


    document.addEventListener(
        "click",
        (event) => {

            if (
                !menuButton ||
                !mobileMenu
            ) {
                return;
            }

            if (
                !mobileMenu.classList.contains(
                    "is-open"
                )
            ) {
                return;
            }

            const clickedMenu =
                mobileMenu.contains(
                    event.target
                );

            const clickedButton =
                menuButton.contains(
                    event.target
                );

            if (
                !clickedMenu &&
                !clickedButton
            ) {
                closeMenu();
            }
        }
    );


    /* =====================================================
       SMOOTH SCROLL
    ===================================================== */

    internalLinks.forEach(
        (link) => {

            link.addEventListener(
                "click",
                (event) => {

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
                            document.querySelector(
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

                    const top =
                        target
                            .getBoundingClientRect()
                            .top
                        +
                        window.scrollY
                        -
                        headerHeight
                        -
                        12;

                    window.scrollTo({
                        top,
                        behavior:
                            "smooth"
                    });

                    closeMenu();
                }
            );
        }
    );


    /* =====================================================
       HEADER SCROLL EFFECT
    ===================================================== */

    const updateHeader = () => {

        if (!header) {
            return;
        }

        header.classList.toggle(
            "is-scrolled",
            window.scrollY > 25
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

    const updateBackButton = () => {

        if (!backToTop) {
            return;
        }

        backToTop.classList.toggle(
            "visible",
            window.scrollY > 650
        );
    };


    updateBackButton();


    window.addEventListener(
        "scroll",
        updateBackButton,
        {
            passive: true
        }
    );


    if (backToTop) {

        backToTop.addEventListener(
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
       REVEAL ANIMATIONS
    ===================================================== */

    const reducedMotion =
        window.matchMedia(
            "(prefers-reduced-motion: reduce)"
        ).matches;


    if (reducedMotion) {

        revealElements.forEach(
            (element) => {

                element.classList.add(
                    "is-visible"
                );
            }
        );

    } else if (
        "IntersectionObserver" in window
    ) {

        const revealObserver =
            new IntersectionObserver(
                (
                    entries,
                    observer
                ) => {

                    entries.forEach(
                        (entry) => {

                            if (
                                !entry.isIntersecting
                            ) {
                                return;
                            }

                            entry.target
                                .classList
                                .add(
                                    "is-visible"
                                );

                            observer.unobserve(
                                entry.target
                            );
                        }
                    );
                },
                {
                    threshold: 0.12,
                    rootMargin:
                        "0px 0px -45px 0px"
                }
            );


        revealElements.forEach(
            (element) => {

                revealObserver.observe(
                    element
                );
            }
        );

    } else {

        revealElements.forEach(
            (element) => {

                element.classList.add(
                    "is-visible"
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

    const navigationLinks =
        document.querySelectorAll(
            '.desktop-nav a[href^="#"]'
        );


    if (
        sections.length &&
        "IntersectionObserver" in window
    ) {

        const sectionObserver =
            new IntersectionObserver(
                (entries) => {

                    entries.forEach(
                        (entry) => {

                            if (
                                !entry.isIntersecting
                            ) {
                                return;
                            }

                            const id =
                                entry.target.id;

                            navigationLinks
                                .forEach(
                                    (link) => {

                                        const active =
                                            link
                                                .getAttribute(
                                                    "href"
                                                )
                                            ===
                                            `#${id}`;

                                        link.classList
                                            .toggle(
                                                "is-active",
                                                active
                                            );
                                    }
                                );
                        }
                    );
                },
                {
                    rootMargin:
                        "-35% 0px -55% 0px",
                    threshold: 0
                }
            );


        sections.forEach(
            (section) => {

                sectionObserver.observe(
                    section
                );
            }
        );
    }


    /* =====================================================
       DESKTOP RESIZE RESET
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
       EXTERNAL LINKS
    ===================================================== */

    const externalLinks =
        document.querySelectorAll(
            'a[target="_blank"]'
        );


    externalLinks.forEach(
        (link) => {

            link.setAttribute(
                "rel",
                "noopener noreferrer"
            );
        }
    );

});
