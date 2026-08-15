<script lang="ts">
  let {form} = $props();
</script>
<form method="POST">
<input type="text" name="id"/>
<input type="password" name="password"/>
<button>login</button>
</form>
{#if form?.error}
<p>{form.error}</p>
{/if}

<a href="/">
home
</a>
