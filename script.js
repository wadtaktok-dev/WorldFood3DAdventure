document.addEventListener("DOMContentLoaded", () => {
  const menuButton = document.querySelector("[data-menu-button]");
  const mobileMenu = document.querySelector("[data-mobile-menu]");
  const navLinks = document.querySelectorAll('a[href^="#"]');
  const revealElements = document.querySelectorAll("[data-reveal]");
  const yearElement = document.querySelector("[data-current-year]");

  // Mobile navigation
  if (menuButton && mobileMenu) {
    menuButton.addEventListener("click", () => {
      const isOpen = mobileMenu.classList.toggle("is-open");

      menuButton.setAttribute(
        "aria-expanded",
        isOpen ? "true" : "false"
      );
    });
  }

  // Smooth scrolling
  navLinks.forEach((link) => {
    link.addEventListener("click", (event) => {
      const targetId = link.getAttribute("href");

      if (!targetId || targetId === "#") return;

      const target = document.querySelector(targetId);

      if (!target) return;

      event.preventDefault();

      target.scrollIntoView({
        behavior: "smooth",
        block: "start",
      });

      if (mobileMenu) {
        mobileMenu.classList.remove("is-open");
      }

      if (menuButton) {
        menuButton.setAttribute("aria-expanded", "false");
      }
    });
  });

  // Reveal animations
  if ("IntersectionObserver" in window) {
    const observer = new IntersectionObserver(
      (entries, revealObserver) => {
        entries.forEach((entry) => {
          if (!entry.isIntersecting) return;

          entry.target.classList.add("is-visible");
          revealObserver.unobserve(entry.target);
        });
      },
      {
        threshold: 0.12,
        rootMargin: "0px 0px -40px 0px",
      }
    );

    revealElements.forEach((element) => {
      observer.observe(element);
    });
  } else {
    revealElements.forEach((element) => {
      element.classList.add("is-visible");
    });
  }

  // Dynamic footer year
  if (yearElement) {
    yearElement.textContent = new Date().getFullYear();
  }

  // Navbar background on scroll
  const header = document.querySelector("[data-header]");

  const updateHeader = () => {
    if (!header) return;

    if (window.scrollY > 20) {
      header.classList.add("is-scrolled");
    } else {
      header.classList.remove("is-scrolled");
    }
  };

  updateHeader();
  window.addEventListener("scroll", updateHeader, {
    passive: true,
  });
});
