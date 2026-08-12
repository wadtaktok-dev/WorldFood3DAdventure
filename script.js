document.addEventListener("DOMContentLoaded", () => {
    const menuButton = document.querySelector("[data-menu-button]");
    const mobileMenu = document.querySelector("[data-mobile-menu]");
    const navLinks = document.querySelectorAll('a[href^="#"]');
    const revealElements = document.querySelectorAll("[data-reveal]");
    const yearElement = document.querySelector("[data-current-year]");
    const header = document.querySelector("[data-header]");

    /* =========================================================
       MOBILE NAVIGATION
    ========================================================= */

    const closeMobileMenu = () => {
        if (!mobileMenu || !menuButton) return;

        mobileMenu.classList.remove("is-open");

        menuButton.setAttribute(
            "aria-expanded",
            "false"
        );
    };

    if (menuButton && mobileMenu) {
        menuButton.addEventListener("click", () => {
            const isOpen =
                mobileMenu.classList.toggle("is-open");

            menuButton.setAttribute(
                "aria-expanded",
                isOpen ? "true" : "false"
            );
        });
    }


    /* =========================================================
       CLOSE MOBILE MENU WHEN CLICKING OUTSIDE
    ========================================================= */

    document.addEventListener("click", (event) => {
        if (!menuButton || !mobileMenu) return;

        const clickedButton =
            menuButton.contains(event.target);

        const clickedMenu =
            mobileMenu.contains(event.target);

        if (!clickedButton && !clickedMenu) {
            closeMobileMenu();
        }
    });


    /* =========================================================
       ESC KEY CLOSES MOBILE MENU
    ========================================================= */

    document.addEventListener("keydown", (event) => {
        if (event.key === "Escape") {
            closeMobileMenu();
        }
    });


    /* =========================================================
       SMOOTH INTERNAL NAVIGATION
    ========================================================= */

    navLinks.forEach((link) => {
        link.addEventListener("click", (event) => {
            const targetId =
                link.getAttribute("href");

            if (
                !targetId ||
                targetId === "#" ||
                !targetId.startsWith("#")
            ) {
                return;
            }

            let target;

            try {
                target =
                    document.querySelector(targetId);
            } catch {
                return;
            }

            if (!target) return;

            event.preventDefault();

            const headerHeight =
                header?.offsetHeight || 0;

            const targetPosition =
                target.getBoundingClientRect().top +
                window.scrollY -
                headerHeight -
                12;

            window.scrollTo({
                top: targetPosition,
                behavior: "smooth"
            });

            closeMobileMenu();
        });
    });


    /* =========================================================
       REVEAL ANIMATIONS
    ========================================================= */

    const prefersReducedMotion =
        window.matchMedia(
            "(prefers-reduced-motion: reduce)"
        ).matches;

    if (prefersReducedMotion) {
        revealElements.forEach((element) => {
            element.classList.add("is-visible");
        });
    } else if ("IntersectionObserver" in window) {
        const revealObserver =
            new IntersectionObserver(
                (entries, observer) => {
                    entries.forEach((entry) => {
                        if (!entry.isIntersecting) {
                            return;
                        }

                        entry.target.classList.add(
                            "is-visible"
                        );

                        observer.unobserve(
                            entry.target
                        );
                    });
                },
                {
                    threshold: 0.12,
                    rootMargin:
                        "0px 0px -40px 0px"
                }
            );

        revealElements.forEach((element) => {
            revealObserver.observe(element);
        });
    } else {
        revealElements.forEach((element) => {
            element.classList.add("is-visible");
        });
    }


    /* =========================================================
       CURRENT YEAR
    ========================================================= */

    if (yearElement) {
        yearElement.textContent =
            new Date().getFullYear();
    }


    /* =========================================================
       HEADER SCROLL STATE
    ========================================================= */

    const updateHeader = () => {
        if (!header) return;

        header.classList.toggle(
            "is-scrolled",
            window.scrollY > 20
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


    /* =========================================================
       ACTIVE NAVIGATION SECTION
    ========================================================= */

    const sections =
        [...document.querySelectorAll("main section[id]")];

    const sectionLinks =
        [...document.querySelectorAll(
            'a[href^="#"]'
        )];

    if (
        sections.length > 0 &&
        "IntersectionObserver" in window
    ) {
        const navigationObserver =
            new IntersectionObserver(
                (entries) => {
                    entries.forEach((entry) => {
                        if (!entry.isIntersecting) {
                            return;
                        }

                        const id =
                            entry.target.id;

                        sectionLinks.forEach((link) => {
                            const isActive =
                                link.getAttribute("href") ===
                                `#${id}`;

                            link.classList.toggle(
                                "is-active",
                                isActive
                            );
                        });
                    });
                },
                {
                    rootMargin:
                        "-30% 0px -60% 0px",
                    threshold: 0
                }
            );

        sections.forEach((section) => {
            navigationObserver.observe(section);
        });
    }


    /* =========================================================
       RESPONSIVE MENU RESET
    ========================================================= */

    window.addEventListener("resize", () => {
        if (window.innerWidth > 760) {
            closeMobileMenu();
        }
    });


    /* =========================================================
       EXTERNAL LINK SECURITY
    ========================================================= */

    const externalLinks =
        document.querySelectorAll(
            'a[target="_blank"]'
        );

    externalLinks.forEach((link) => {
        const rel =
            new Set(
                (link.getAttribute("rel") || "")
                    .split(/\s+/)
                    .filter(Boolean)
            );

        rel.add("noopener");
        rel.add("noreferrer");

        link.setAttribute(
            "rel",
            [...rel].join(" ")
        );
    });
});
