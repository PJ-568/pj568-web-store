// 使用 IIFE 包裹以避免全局变量污染
(function () {
  var loadingBar = document.querySelector(".loading-bar");
  var progress = document.querySelector(".loading-bar .progress");
  var timer = null;
  let pjax;

  // 初始化加载动画相关元素
  function initAni() {
    loadingBar = document.querySelector(".loading-bar");
    progress = document.querySelector(".loading-bar .progress");
  }

  // 停止加载动画
  function endLoad() {
    clearInterval(timer);
    progress.style.width = "100%";
    loadingBar.classList.remove("loading");

    setTimeout(function () {
      progress.style.width = 0;
    }, 400);
  }

  function handleError(responseText) {
    try {
      const msg = document.createElement("div");
      msg.innerHTML = responseText;
      const ctn = document.querySelector(".container");
      ctn.insertBefore(msg, ctn.children[0])
    } catch (e) {
      alert("Error: " + e);
    }
  }

  // 初始化 PJAX
  function initPjax() {
    try {
      const Pjax = window.Pjax || function () { };
      pjax = new Pjax({
        selectors: [
          "head meta",
          "head title",
          "nav .ul",
          ".container",
          ".pjax-reload"
        ],
        switches: {
          ".container": Pjax.switches.sideBySide
        },
        switchesOptions: {
          ".container": {
            classNames: {
              remove: "Animated Animated-remove Animated--reverse Animate--fast Animate--noDelay",
              add: "Animated",
              backward: "Animate--slideInRight",
              forward: "Animate--slideInLeft"
            }
          }
        },
        // currentUrlFullReload: true,
        cacheBust: false
      });

      pjax._handleResponse = pjax.handleResponse;

      pjax.handleResponse = function (responseText, request, href, options) {
        if (request.responseText.match("<html")) {
          pjax._handleResponse(responseText, request, href, options);
        } else {
          handleError(responseText);
        }
      }
    } catch (e) {
      console.log('PJAX 初始化出错：' + e);
    }
  }

  // translate-js 翻译系统
  //// 初始化翻译系统
  function initTranslate() {
    try {
      translate.service.use('client.edge');
      translate.listener.start();
      translate.setAutoDiscriminateLocalLanguage();
      translate.language.setLocal('chinese_simplified');
      translate.language.setUrlParamControl();
      translate.ignore.class.push('notTranslate');
      translate.nomenclature.append('chinese_simplified', 'english', `
                主页=home
                关于=about
            `);
      translate.execute();
    } catch (e) {
      console.error('翻译系统出错：' + e);
    }
  }

  // 初始化
  function initialize() {
    initAni();
    initPjax();
    initTranslate();
  }

  // 触发器
  //// 网页加载完毕后触发
  window.addEventListener('DOMContentLoaded', () => initialize());
  //// Pjax 开始时执行的函数
  document.addEventListener("pjax:send", function () {
    var loadingBarWidth = 20;
    var MAX_LOADING_WIDTH = 95;

    loadingBar.classList.add("loading");
    progress.style.width = loadingBarWidth + "%";

    clearInterval(timer);
    timer = setInterval(function () {
      loadingBarWidth += 3;

      if (loadingBarWidth > MAX_LOADING_WIDTH) {
        loadingBarWidth = MAX_LOADING_WIDTH;
      }

      progress.style.width = loadingBarWidth + "%";
    }, 500);
  });
  //// 翻译执行完成后触发
  translate.listener.renderTaskFinish = function (_task) {
    if (document.getElementById("giscus-container") != null) {
      SetupGiscus(getCurrentLanguage());   //// 初始化评论系统
    }
  }
  //// 监听 Pjax 完成后加载
  document.addEventListener("pjax:complete", function () {
    endLoad();
    if (document.getElementById("giscus-container") != null) {
      SetupGiscus(getCurrentLanguage());   //// 初始化评论系统
    }
    translate.execute();
    translate.selectLanguageTag.refreshRender();
  });
})();