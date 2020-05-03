/**
 * @param {string} title Sidebar Title
 * @param {string[]} routes Routes
 * @param {boolean} [collapsable] Collapsible Title
 * @returns {{}}
 */
function generateSidebar(title, routes, collapsable = false) {
  return [{
    title,
    collapsable,
    children: routes,
  }]
}

module.exports = {
  base: "/beatwalls/",
  head: [
    ['link', { rel: 'icon', href: '/favicon.png' }],
    ['meta', { name: 'theme-color', content: '#2196f3' }],
  ],

  locales: {
    '/': {
      lang: 'en-US',
      title: 'BW Docs',
    },
    '/ja/': {
      lang: 'ja-JA',
      title: 'BW Docs',
    },
  },

  theme: 'yuu',
  themeConfig: {
    yuu: {
      defaultColorTheme: 'blue',
    },

    repo: 'spookyGh0st/beatwalls',
    docsDir: 'docs',
    editLinks: true,

    displayAllHeaders: true,

    locales: {
      '/': {
        selectText: 'Language',
        label: 'English',
        ariaLabel: 'Language',
        editLinkText: 'Help improve this page!',
        lastUpdated: 'Last Updated',
        nav: [
          { text: 'Home', link: '/' },
          { text: 'Guide', link: '/guide/' },
          { text: 'Examples', link: '/examples/' },
          { text: 'Reference', link: '/reference/' },
        ],
        sidebar: {
          '/guide/': generateSidebar('Guide', [
            '',
            'assertions',
            'custom-wallstructures',
            'advanced-usage',
          ]),
          '/changelog/': generateSidebar('Changelog', [
            '',
            'history',
          ]),
          }
      },
      '/ja/': {
        selectText: 'Language',
        label: 'Japanese',
        ariaLabel: 'Language',
        editLinkText: 'Help improve this page!',
        lastUpdated: 'Last Updated',
        nav: [
          { text: 'Home', link: '/ja/' },
          { text: 'Guide', link: '/ja/guide/' },
          { text: 'Examples', link: '/ja/examples/' },
          { text: 'Reference', link: '/ja/reference/' },
        ],
        sidebar: {
          '/ja/guide/': generateSidebar('Guide', [
            '',
            'assertions',
            'custom-wallstructures',
            //'functions',
            //'constants',
            //'options',
          ]),
          '/ja/changelog/': generateSidebar('Changelog', [
            '',
            'history',
          ]),
          }
      },
    },
  },
  plugins: [
    ['@vuepress/last-updated', {
      transformer: timestamp => {
        const dateformat = require('dateformat')
        return dateformat(timestamp, 'yyyy/mm/dd hh:MM:ss TT')
      },
    }],
    ['@vuepress/medium-zoom', {
      options: {
        margin: 8,
        background: '#21253073',
      },
    }],
    '@vuepress/nprogress',
    ['container', {
      type: 'feature',
      before: info => `<div class="feature"><h2>${info}</h2>`,
      after: '</div>',
    }],
    ['named-chunks', {
      pageChunkName: ({ key }) => `page${key.slice(1)}`,
      layoutChunkName: ({ componentName }) => `layout-${componentName}`,
    }],
    ['vuepress-plugin-code-copy', true],
    'seo',
  ],
  configureWebpack: {
    resolve: {
      alias: {
        '@': '../',
      },
    },
  },
}
