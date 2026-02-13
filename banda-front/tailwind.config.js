/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,ts}"],
  theme: {
    extend: {
      colors: {
        festive: "#C8102E",
        light: "#E63946",

        yellorange: "#F59E0B",
        copper: "#FFB400",
        yellow: "#FFD166",

        white: "#FFFFFF",
        lightgray: "#F2F2F2",
        dark: "#333333",

        success: "#228041",
        warning: "#FFFF66",
        danger: "#E6175B",

        night: "#063575",
        beige: "#F7EEDB",
      }
    },
  },
  plugins: [],
}

