// axiosPlugin.d.ts
declare module "./axiosPlugin" {
  import { App } from "vue";

  interface AxiosOptions {
    baseUrl?: string;
  }

  const axiosPlugin: {
    install: (app: App, options: AxiosOptions) => void;
  };

  export default axiosPlugin;
}
